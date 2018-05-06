package main;

import java.util.List;

public interface TrainDistance {

    MinStep NO_SUCH_ROUTE = new MinStep(false, -1);

    int getDistanceByRoute(String route);

    MinStep getMinStep(String start, String end);

    MinStep getRouteByStops(String start, String end, int maxStops);

    MinStep getRouteByDistance(String start, String end, int maxDistance);


}
