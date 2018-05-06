package main;

import java.util.List;
import java.util.Map;

public class MinStep {

    private boolean reachable;//是否可达
    private int minStep;//最短步长
    private List<String> minRoute;//最短路径
    private Map<String, Integer> allRoute;

    public MinStep() {
    }

    public MinStep(boolean reachable, int minStep) {
        this.reachable = reachable;
        this.minStep = minStep;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    public int getMinStep() {
        return minStep;
    }

    public void setMinStep(int minStep) {
        this.minStep = minStep;
    }

    public List<String> getMinRoute() {
        return minRoute;
    }

    public void setMinRoute(List<String> minRoute) {
        this.minRoute = minRoute;
    }

    public Map<String, Integer> getAllRoute() {
        return allRoute;
    }

    public void setAllRoute(Map<String, Integer> allRoute) {
        this.allRoute = allRoute;
    }
}
