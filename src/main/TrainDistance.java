package main;

import java.util.Map;

public interface TrainDistance {

    MinStep NO_SUCH_ROUTE = new MinStep(false, -1);

    int getDistanceByRoute(String route);

    MinStep getMinStep(String start, String end);

    Map<String, Integer> getRouteByStops(String start, String end, int maxStops);

    Map<String, Integer> getRouteByDistance(String start, String end, int maxDistance);


}
