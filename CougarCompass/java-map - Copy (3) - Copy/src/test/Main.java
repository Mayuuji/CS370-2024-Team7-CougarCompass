package test;

import data.RoutingData;
import data.RoutingService;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import waypoint.EventWaypoint;
import waypoint.MyWaypoint;
import waypoint.WaypointRender;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main extends javax.swing.JFrame {

    private final Set<MyWaypoint> waypoints = new HashSet<>();
    private List<RoutingData> routingData = new ArrayList<>();
    private EventWaypoint event;
    private Point mousePosition;
    private final Map<String, GeoPosition> buildingLocations = new HashMap<>();
    

    public Main() {
        initComponents();
        init();
        addCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCourseActionPerformed(evt);
            }
        }); 
        calculateRouteButton.addActionListener(evt -> calculateRoute());
        startLiveTime(); 
        exitProgram.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        exitProgramActionPerformed(evt);
    }
});
    }
   private void exitProgramActionPerformed(java.awt.event.ActionEvent evt) {
    System.exit(0); // Close the program immediately
} 
private void addCourseActionPerformed(java.awt.event.ActionEvent evt) {
// Create a new JFrame for the pop-up window
    javax.swing.JFrame popupFrame = new javax.swing.JFrame("Add a Course");

    // Create components
    javax.swing.JLabel nameLabel = new javax.swing.JLabel("Enter course name:");
    javax.swing.JLabel buildingLabel = new javax.swing.JLabel("Select building location:");
    javax.swing.JTextField courseNameInput = new javax.swing.JTextField(20);
    javax.swing.JComboBox<String> buildingDropdown = new javax.swing.JComboBox<>();
    buildingDropdown.addItem("Markstein Hall (33.12801842802658, -117.1578669600504)");
    buildingDropdown.addItem("Arts Building (33.13004516351822, -117.15806402065996)");
    buildingDropdown.addItem("Center for Children & Families (33.132720057964, -117.15287080683505)");
    buildingDropdown.addItem("M. Gordon Clarke Fieldhouse (33.131618789528005, -117.16008541064545)");
    buildingDropdown.addItem("Craven Hall (33.12796420196285, -117.15968868090317)");
    buildingDropdown.addItem("Foundation Classroom Buildings (33.127348936562235, -117.15785931598981)");
    buildingDropdown.addItem("McMahan House (33.1287148198879, -117.16286986569031)");
    buildingDropdown.addItem("Parking Structure 1 (33.132363223874925, -117.15733034300663)");
    buildingDropdown.addItem("Science Hall 1 (33.127779622783216, -117.1587335907895)");
    buildingDropdown.addItem("Science Hall 2 (33.1305236596975, -117.15780054121473)");
    buildingDropdown.addItem("Social & Behavioral Sciences Building (33.13064055622676, -117.15725687453728)");
    buildingDropdown.addItem("The Sports Center (33.13208021651548, -117.15951970342282)");
    buildingDropdown.addItem("Student Health & Counseling Services Building (33.13131732255312, -117.15821931150008)");
    buildingDropdown.addItem("University Commons (33.12734893657725, -117.15957847820027)");
    buildingDropdown.addItem("Public Safety Building (Police) (33.13407050653009, -117.15436953753878)");
    buildingDropdown.addItem("University Services Building (33.13369522126397, -117.15381852401069)");
    buildingDropdown.addItem("University Student Union (33.130133007403124, -117.15904947912209)");
    buildingDropdown.addItem("University Village Apartments (33.1331538232905, -117.15747725385495)");
    buildingDropdown.addItem("Veterans Center (33.12763506483282, -117.15818255116729)");

    javax.swing.JButton submitButton = new javax.swing.JButton("Submit");

    // Add action listener to the submit button
    submitButton.addActionListener(e -> {
        String courseName = courseNameInput.getText();
        String selectedBuilding = (String) buildingDropdown.getSelectedItem();
        if (!courseName.isEmpty() && selectedBuilding != null) {
            // Extract coordinates from the selected building string
            String[] parts = selectedBuilding.split("[(,)]");
            double latitude = Double.parseDouble(parts[1].trim());
            double longitude = Double.parseDouble(parts[2].trim());
            GeoPosition geoPosition = new GeoPosition(latitude, longitude);

            // Add to dropdowns and store in the buildingLocations map
            String courseDetails = courseName + " (" + selectedBuilding + ")";
            startingClass.addItem(courseDetails);
            endingClass.addItem(courseDetails);
            buildingLocations.put(courseDetails, geoPosition);

            JOptionPane.showMessageDialog(popupFrame, 
                "Course added:\nName: " + courseName + "\nBuilding: " + selectedBuilding);
            popupFrame.dispose(); // Close the pop-up window
        } else {
            JOptionPane.showMessageDialog(popupFrame, "Please fill all fields.");
        }
    });

    // Create a JPanel and add components
    javax.swing.JPanel panel = new javax.swing.JPanel();
    panel.setLayout(new java.awt.GridLayout(3, 2, 10, 10));
    panel.add(nameLabel);
    panel.add(courseNameInput);
    panel.add(buildingLabel);
    panel.add(buildingDropdown);
    panel.add(new javax.swing.JLabel()); // Placeholder for spacing
    panel.add(submitButton);

    // Set up the JFrame
    popupFrame.add(panel);
    popupFrame.setSize(400, 200);
    popupFrame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
    popupFrame.setVisible(true);
    }
private Timer timeUpdater;
private void startLiveTime() {
    // Create a formatter for the time
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

    // Initialize the Timer
    timeUpdater = new Timer(1000, evt -> {
        String currentTimeString = timeFormat.format(new Date());
        currentTime.setText("Current Time: " + currentTimeString);
    });

    // Start the timer
    timeUpdater.start();
}
private void calculateRoute() { // **NEWLY ADDED**
    String startingCourse = (String) startingClass.getSelectedItem();
    String endingCourse = (String) endingClass.getSelectedItem();

    if (startingCourse != null && endingCourse != null
        && buildingLocations.containsKey(startingCourse)
        && buildingLocations.containsKey(endingCourse)) {

        GeoPosition startPosition = buildingLocations.get(startingCourse);
        GeoPosition endPosition = buildingLocations.get(endingCourse);

        // Fetch route using the RoutingService
        List<RoutingData> route = RoutingService.getInstance().routing(
            startPosition.getLatitude(), 
            startPosition.getLongitude(), 
            endPosition.getLatitude(), 
            endPosition.getLongitude()
        );

        // Set the route on the map viewer
        jXMapViewer.setRoutingData(route);

        // Calculate travel time
        double totalDistance = 0; // in meters
        for (RoutingData data : route) {
            totalDistance += data.getDistance();
        }

        // Convert distance to miles (1 meter = 0.000621371 miles)
        double distanceInMiles = totalDistance * 0.000621371;

        // Average walking speed in mph
        double walkingSpeed = 2.3;

        // Calculate time in hours
        double timeInHours = distanceInMiles / walkingSpeed;

        // Convert time to minutes
        int timeInMinutes = (int) Math.round(timeInHours * 60);

        // Update travelTime label
        travelTime.setText("Travel Time: " + timeInMinutes + " min");

        // Calculate ETA
        LocalTime currentTime = LocalTime.now();
        LocalTime eta = currentTime.plusMinutes(timeInMinutes);

        // Format time to display as HH:mm:ss a
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedETA = eta.format(formatter);

        // Update estimatedETA label
        estimatedETA.setText("Estimated ETA: " + formattedETA);

    } else {
        JOptionPane.showMessageDialog(this, "Please select valid start and end courses.");
    }
    }

    private void init() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(33.1290377,-117.160871);
        jXMapViewer.setAddressLocation(geo);
        jXMapViewer.setZoom(2);

        //  Create event mouse move
        MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(mm);
        jXMapViewer.addMouseMotionListener(mm);
        jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
        event = getEvent();
    }

    private void addWaypoint(MyWaypoint waypoint) {
        for (MyWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
        Iterator<MyWaypoint> iter = waypoints.iterator();
        while (iter.hasNext()) {
            if (iter.next().getPointType() == waypoint.getPointType()) {
                iter.remove();
            }
        }
        waypoints.add(waypoint);
        initWaypoint();
    }

    private void initWaypoint() {
        WaypointPainter<MyWaypoint> wp = new WaypointRender();
        wp.setWaypoints(waypoints);
        jXMapViewer.setOverlayPainter(wp);
        for (MyWaypoint d : waypoints) {
            jXMapViewer.add(d.getButton());
        }
        //  Routing Data
        if (waypoints.size() == 2) {
            GeoPosition start = null;
            GeoPosition end = null;
            for (MyWaypoint w : waypoints) {
                if (w.getPointType() == MyWaypoint.PointType.START) {
                    start = w.getPosition();
                } else if (w.getPointType() == MyWaypoint.PointType.END) {
                    end = w.getPosition();
                }
            }
            if (start != null && end != null) {
                routingData = RoutingService.getInstance().routing(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude());

            } else {
                routingData.clear();
            }
            jXMapViewer.setRoutingData(routingData);
        }
    }

    private void clearWaypoint() {
        for (MyWaypoint d : waypoints) {
            jXMapViewer.remove(d.getButton());
        }
        routingData.clear();
        waypoints.clear();
        initWaypoint();
    }

    private EventWaypoint getEvent() {
        return new EventWaypoint() {
            @Override
            public void selected(MyWaypoint waypoint) {
                JOptionPane.showMessageDialog(Main.this, waypoint.getName());
            }
        };
    }
    
       // Additional components and listeners
  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        menuStart = new javax.swing.JMenuItem();
        menuEnd = new javax.swing.JMenuItem();
        jXMapViewer = new data.JXMapViewerCustom();
        comboMapType = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        startingClass = new javax.swing.JComboBox<>();
        endingClass = new javax.swing.JComboBox<>();
        addCourse = new javax.swing.JButton();
        exitProgram = new javax.swing.JButton();
        backing1 = new javax.swing.JPanel();
        travelTime = new javax.swing.JLabel();
        backing2 = new javax.swing.JPanel();
        currentTime = new javax.swing.JLabel();
        backing3 = new javax.swing.JPanel();
        estimatedETA = new javax.swing.JLabel();
        calculateRouteButton = new javax.swing.JButton();

        menuStart.setText("Start");
        menuStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuStartActionPerformed(evt);
            }
        });
        jPopupMenu1.add(menuStart);

        menuEnd.setText("End");
        menuEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEndActionPerformed(evt);
            }
        });
        jPopupMenu1.add(menuEnd);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jXMapViewer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jXMapViewerMouseReleased(evt);
            }
        });

        comboMapType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Open Street", "Virtual Earth", "Hybrid", "Satellite" }));
        comboMapType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboMapTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(jXMapViewer);
        jXMapViewer.setLayout(jXMapViewerLayout);
        jXMapViewerLayout.setHorizontalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addContainerGap(1010, Short.MAX_VALUE)
                .addComponent(comboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jXMapViewerLayout.setVerticalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/CCL.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        startingClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enter Starting Class" }));
        startingClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startingClassActionPerformed(evt);
            }
        });

        endingClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enter Ending Class" }));

        addCourse.setText("Add Course");

        exitProgram.setText("Close Program");

        travelTime.setText("Travel Time:");

        javax.swing.GroupLayout backing1Layout = new javax.swing.GroupLayout(backing1);
        backing1.setLayout(backing1Layout);
        backing1Layout.setHorizontalGroup(
            backing1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backing1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(travelTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        backing1Layout.setVerticalGroup(
            backing1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backing1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(travelTime, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        currentTime.setText("Current Time:");

        javax.swing.GroupLayout backing2Layout = new javax.swing.GroupLayout(backing2);
        backing2.setLayout(backing2Layout);
        backing2Layout.setHorizontalGroup(
            backing2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backing2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(currentTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        backing2Layout.setVerticalGroup(
            backing2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backing2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(currentTime, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        estimatedETA.setText("Estimated ETA:");

        javax.swing.GroupLayout backing3Layout = new javax.swing.GroupLayout(backing3);
        backing3.setLayout(backing3Layout);
        backing3Layout.setHorizontalGroup(
            backing3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backing3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(estimatedETA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        backing3Layout.setVerticalGroup(
            backing3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backing3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(estimatedETA, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        calculateRouteButton.setText("Calculate Route");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startingClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(endingClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(backing1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(backing2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(backing3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(addCourse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(calculateRouteButton, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(61, 61, 61))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(exitProgram)
                                .addGap(66, 66, 66))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(startingClass, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(endingClass, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(addCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(calculateRouteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backing1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backing2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backing3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(exitProgram)
                .addContainerGap(296, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jXMapViewer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboMapTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboMapTypeActionPerformed
        TileFactoryInfo info;
        int index = comboMapType.getSelectedIndex();
        if (index == 0) {
            info = new OSMTileFactoryInfo();
        } else if (index == 1) {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        } else if (index == 2) {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
        } else {
            info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        }
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
    }//GEN-LAST:event_comboMapTypeActionPerformed

    private void menuStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuStartActionPerformed
        GeoPosition geop = jXMapViewer.convertPointToGeoPosition(mousePosition);
        MyWaypoint wayPoint = new MyWaypoint("Start Location", MyWaypoint.PointType.START, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()));
        addWaypoint(wayPoint);
    }//GEN-LAST:event_menuStartActionPerformed

    private void menuEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEndActionPerformed
        GeoPosition geop = jXMapViewer.convertPointToGeoPosition(mousePosition);
        MyWaypoint wayPoint = new MyWaypoint("End Location", MyWaypoint.PointType.END, event, new GeoPosition(geop.getLatitude(), geop.getLongitude()));
        addWaypoint(wayPoint);
    }//GEN-LAST:event_menuEndActionPerformed

    private void jXMapViewerMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXMapViewerMouseReleased
        if (SwingUtilities.isRightMouseButton(evt)) {
            mousePosition = evt.getPoint();
            jPopupMenu1.show(jXMapViewer, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jXMapViewerMouseReleased

    
    
    
    private void startingClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startingClassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startingClassActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
            
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCourse;
    private javax.swing.JPanel backing1;
    private javax.swing.JPanel backing2;
    private javax.swing.JPanel backing3;
    private javax.swing.JButton calculateRouteButton;
    private javax.swing.JComboBox<String> comboMapType;
    private javax.swing.JLabel currentTime;
    private javax.swing.JComboBox<String> endingClass;
    private javax.swing.JLabel estimatedETA;
    private javax.swing.JButton exitProgram;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private data.JXMapViewerCustom jXMapViewer;
    private javax.swing.JMenuItem menuEnd;
    private javax.swing.JMenuItem menuStart;
    private javax.swing.JComboBox<String> startingClass;
    private javax.swing.JLabel travelTime;
    // End of variables declaration//GEN-END:variables
}
