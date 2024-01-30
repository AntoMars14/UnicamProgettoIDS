package it.unicam.view.io;

import it.unicam.model.Coordinates;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MapHandler {

    private final JMapViewer mapViewer;
    private final JButton confirmButton;
    private final JFrame frame;

    private ICoordinate selectedCoordinates;
    private MapMarker marker;
    private List<Coordinates> positionsToShow;
    private CountDownLatch frameDisposedLatch;

    public MapHandler(List<Coordinates> positionsToShow) {
        this.positionsToShow = positionsToShow;
        mapViewer = new JMapViewer();
        mapViewer.addMouseListener(new MapMouseListener());

        confirmButton = new JButton("Confirm and Close");
        confirmButton.addActionListener(e -> {
            closeMap();
        });

        JScrollPane scrollPane = new JScrollPane(mapViewer);

        InputMap inputMap = mapViewer.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = mapViewer.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("UP"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");

        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapViewer.moveMap(0, -50);
            }
        });

        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapViewer.moveMap(0, 50);
            }
        });

        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapViewer.moveMap(-50, 0);
            }
        });

        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapViewer.moveMap(50, 0);
            }
        });

        frame = new JFrame("Map");
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(confirmButton, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mapViewer.setDisplayPosition(new Coordinate(43.1344,13.0667), 16);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frameDisposedLatch = new CountDownLatch(1);
    }

    public ICoordinate showMap() {
        for (Coordinates position : positionsToShow) {
            MapMarkerDot markers = new MapMarkerDot(position.getLat(), position.getLon());
            mapViewer.addMapMarker(markers);
        }

        marker = null;
        frame.setVisible(true);
        try {
            frameDisposedLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(selectedCoordinates);
        return selectedCoordinates;
    }

    private void closeMap() {
        mapViewer.removeAllMapMarkers();
        frame.dispose();
        frameDisposedLatch.countDown();
    }

    private class MapMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                Point clickPoint = e.getPoint();
                selectedCoordinates = mapViewer.getPosition(clickPoint.x, clickPoint.y);

                mapViewer.removeMapMarker(marker);

                marker = new MapMarkerDot(selectedCoordinates.getLat(), selectedCoordinates.getLon());
                mapViewer.addMapMarker(marker);
            }
        }
    }
}
