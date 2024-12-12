package data;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.ResponsePath;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;
import com.graphhopper.util.Translation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.io.File;
import com.graphhopper.routing.util.EncodingManager;




public class RoutingService {

    private static RoutingService instance;
    private final GraphHopper hopper;

    public static RoutingService getInstance() {
        if (instance == null) {
            instance = new RoutingService();
        }
        return instance;
    }

    private RoutingService() {
        hopper = createGraphHopperInstance("osm file/csusm.osm");
    }


    private GraphHopper createGraphHopperInstance(String ghLoc) {
    GraphHopper graHopper = new GraphHopper();
    graHopper.setOSMFile(ghLoc); // Path to your OSM file
    graHopper.setGraphHopperLocation("target/routing-graph-cache"); // Cache location

    // Define vehicle profiles directly
    graHopper.setProfiles(
        new Profile("foot").setVehicle("foot").setWeighting("fastest").setTurnCosts(false)
    );

    // Add Contraction Hierarchies for each profile
    graHopper.getCHPreparationHandler().setCHProfiles(
        new CHProfile("foot")
    );

    graHopper.importOrLoad(); // Load or build the graph
    return graHopper;
    }


    public List<RoutingData> routing(double fromLat, double fromLon, double toLat, double toLon) {
        // simple configuration of the request object
        GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon).
                // note that we have to specify which profile we are using even when there is only one like here
                setProfile("foot").
                // define the language for the turn instructions
                setLocale(Locale.US);
        GHResponse rsp = hopper.route(req);

        // handle errors
        if (rsp.hasErrors()) {
            throw new RuntimeException(rsp.getErrors().toString());
        }

        // use the best path, see the GHResponse class for more possibilities.
        ResponsePath path = rsp.getBest();

        // points, distance in meters and time in millis of the full path
        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();

        Translation tr = hopper.getTranslationMap().getWithFallBack(Locale.UK);
        InstructionList il = path.getInstructions();
        // iterate over all turn instructions
        List<RoutingData> list = new ArrayList<>();
        for (Instruction instruction : il) {
            // System.out.println("distance " + instruction.getDistance() + " for instruction: " + instruction.getTurnDescription(tr));
            list.add(new RoutingData(instruction.getDistance(), instruction.getTurnDescription(tr), instruction.getPoints()));
        }
        return list;
    }
}
