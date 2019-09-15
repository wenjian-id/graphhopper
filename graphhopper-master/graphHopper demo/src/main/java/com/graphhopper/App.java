package com.graphhopper;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.PointList;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        // create one GraphHopper instance
        GraphHopper hopper = new GraphHopperOSM().forServer();
        hopper.setDataReaderFile("bhutan-latest.osm");
        // where to store graphhopper files?
        hopper.setGraphHopperLocation("resources");
        hopper.setEncodingManager(EncodingManager.create("car"));

        // now this can take minutes if it imports or a few seconds for loading
        // of course this is dependent on the area you import
        hopper.importOrLoad();
        System.out.println( "load success" );
        // simple configuration of the request object, see the GraphHopperServlet classs for more possibilities.
        GHRequest req = new GHRequest(27.3766,89.5880, 27.2937,91.4557).
                setWeighting("fastest").
                setVehicle("car").
                setLocale(Locale.CHINA);
        GHResponse rsp = hopper.route(req);

        // first check for errors
        if(rsp.hasErrors()) {
            // handle them!
            // rsp.getErrors()
            return;
        }

        // use the best path, see the GHResponse class for more possibilities.
        PathWrapper path = rsp.getBest();
        System.out.println( "get path" );
        // points, distance in meters and time in millis of the full path
        PointList pointList = path.getPoints();
        double distance = path.getDistance();
        long timeInMs = path.getTime();
        System.out.println( "pointList" );
        System.out.println(pointList);

        InstructionList il = path.getInstructions();
        // iterate over every turn instruction
        int i = 0;
        for(Instruction instruction : il) {
            instruction.getDistance();
            System.out.println("index:"+i);
            i++;
            System.out.println(instruction.getDistance());
        }

    }
}
