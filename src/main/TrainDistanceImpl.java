package main;

import java.util.*;

public class TrainDistanceImpl implements TrainDistance {

    //key1节点编号，key2节点编号，value为key1到key2的步长
    private HashMap<String, HashMap<String, Integer>> stepLength;
    //非独立节点个数
    private int nodeNum;
    //移除节点
    private HashSet<String> outNode;
    //起点到各点的步长，key为目的节点，value为到目的节点的步长
    private HashMap<String, PreNode> nodeStep;
    //下一次计算的节点
    private LinkedList<String> nextNode;
    //起点、终点
    private String startNode;
    private String endNode;
    private Map<String, Integer> route;
    private Stack<String> cacheRoute;


    @Override
    public MinStep getMinStep(String start, String end) {
        this.stepLength = initStepLength();
        this.nodeNum = this.stepLength != null ? this.stepLength.size() : 0;
        //起点、终点不在目标节点内，返回不可达
        if (this.stepLength == null || (!this.stepLength.containsKey(start)) || (!this.stepLength.containsKey(end))) {
            return NO_SUCH_ROUTE;
        }
        initProperty(start, end);
        step();
        if (nodeStep.containsKey(end)) {
            return changeToMinStep();
        }
        return NO_SUCH_ROUTE;
    }

    @Override
    public Map<String, Integer> getRouteByStops(String start, String end, int maxStops) {
        this.stepLength = initStepLength();
        this.nodeNum = this.stepLength != null ? this.stepLength.size() : 0;
        initProperty(start, end);
        getRoute(maxStops);
        return route;
    }

    @Override
    public Map<String, Integer> getRouteByDistance(String start, String end, int maxDistance) {
        this.stepLength = initStepLength();
        this.nodeNum = this.stepLength != null ? this.stepLength.size() : 0;
        initProperty(start, end);
        getRouteByDis(maxDistance);
        return route;
    }

    @Override
    public int getDistanceByRoute(String route) {
        String[] routeArr = route.split("-");
        this.stepLength = initStepLength();
        int step = 0;
        boolean reachable = false;
        for (int i = 0; i < routeArr.length - 1; i++) {
            reachable = false;
            String node = routeArr[i];
            String nextNode = routeArr[i + 1];
            HashMap<String, Integer> nextStep = stepLength.get(node);
            Iterator<Map.Entry<String, Integer>> iterator = nextStep.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> next = iterator.next();
                if (next.getKey().equals(nextNode)) {
                    step += next.getValue();
                    reachable = true;
                }
            }
            if (!reachable) {
                break;
            }
        }

        return reachable ? step : -1;
    }

    /**
     * 返回最短距离以及路径
     */
    private MinStep changeToMinStep() {
        MinStep minStep = new MinStep();
        minStep.setMinStep(nodeStep.get(endNode).getNodeStep());
        minStep.setReachable(true);
        LinkedList<String> step = new LinkedList<>();
        minStep.setMinRoute(step);
        String nodeNum = endNode;
        step.addFirst(nodeNum);
        while (nodeStep.containsKey(nodeNum)) {
            String node = nodeStep.get(nodeNum).getPreNodeNum();
            step.addFirst(node);
            nodeNum = node;
            if (node.equals(startNode)) {
                break;
            }
        }
        return minStep;
    }

    /**
     * @param start
     * @Author:lulei
     * @Description: 初始化属性
     */
    private void initProperty(String start, String end) {
        outNode = new HashSet<>();
        nodeStep = new HashMap<>();
        nextNode = new LinkedList<>();
        route = new HashMap<>();
        cacheRoute = new Stack<>();
        cacheRoute.push(start);
        nextNode.add(start);
        startNode = start;
        endNode = end;
    }

    private void step() {
        if (nextNode == null || nextNode.size() < 1) {
            return;
        }
        if (outNode.size() == nodeNum) {
            return;
        }
        //获取下一个计算节点
        String start = nextNode.removeFirst();
        //到达该节点的最小距离
        int step = 0;
        if (nodeStep.containsKey(start)) {
            step = nodeStep.get(start).getNodeStep();
        }
        //获取该节点可达节点
        HashMap<String, Integer> nextStep = stepLength.get(start);
        Iterator<Map.Entry<String, Integer>> iterator = nextStep.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String key = entry.getKey();
            //起点到可达节点的距离
            Integer value = entry.getValue() + step;
            if ((!nextNode.contains(key)) && (!outNode.contains(key)) && (!startNode.equals(key))) {
                nextNode.add(key);
            }
            if (nodeStep.containsKey(key)) {
                if (value < nodeStep.get(key).getNodeStep()) {
                    nodeStep.put(key, new PreNode(start, value));
                }
            } else {
                nodeStep.put(key, new PreNode(start, value));
            }
        }
        //将该节点移除
        outNode.add(start);
        //计算下一个节点
        step();
    }

    private void getRoute(int maxStep) {
        String start = cacheRoute.peek();
        boolean isBreak = false;
        int step = 0;
        if (nodeStep.containsKey(start)) {
            step = nodeStep.get(start).getNodeStep();
        }
        if (cacheRoute.isEmpty()) {
            return;
        }
        HashMap<String, Integer> nextStep = stepLength.get(start);
        Iterator<Map.Entry<String, Integer>> iterator = nextStep.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String key = entry.getKey();
            Integer value = entry.getValue() + step;
            if (cacheRoute.size() > maxStep) {
                cacheRoute.pop();
                isBreak = true;
                break;
            }
            if (endNode.equals(key)) {
                cacheRoute.push(key);
                String currentRoute = cacheRoute.toString();
                route.put(currentRoute, value);
            } else {
                cacheRoute.push(key);
            }
            nodeStep.put(key, new PreNode(start, value));
            getRoute(maxStep);
        }
        if (!iterator.hasNext() && cacheRoute.size() > 1 && !isBreak) {
            cacheRoute.pop();
        }
    }

    private void getRouteByDis(int maxDistance) {
        String start = cacheRoute.peek();
        int step = 0;
        boolean isBreak = false;
        if (nodeStep.containsKey(start)) {
            step = nodeStep.get(start).getNodeStep();
        }
        HashMap<String, Integer> nextStep = stepLength.get(start);
        Iterator<Map.Entry<String, Integer>> iterator = nextStep.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            String key = entry.getKey();
            Integer value = entry.getValue() + step;
            if (value >= maxDistance) {
                cacheRoute.pop();
                isBreak = true;
                break;
            }
            if (endNode.equals(key)) {
                cacheRoute.push(key);
                String currentRoute = cacheRoute.toString();
                route.put(currentRoute, value);
            } else {
                cacheRoute.push(key);
            }
            nodeStep.put(key, new PreNode(start, value));
            getRouteByDis(maxDistance);
        }
        if (!iterator.hasNext() && cacheRoute.size() > 1 && !isBreak) {
            cacheRoute.pop();
        }
    }


    public HashMap<String, HashMap<String, Integer>> initStepLength() {
        stepLength = new HashMap<>();
        HashMap<String, Integer> stepA = new HashMap<>();
        stepA.put("B", 5);
        stepA.put("D", 5);
        stepA.put("E", 7);
        HashMap<String, Integer> stepB = new HashMap<>();
        stepB.put("C", 4);
        HashMap<String, Integer> stepC = new HashMap<>();
        stepC.put("D", 8);
        stepC.put("E", 2);
        HashMap<String, Integer> stepD = new HashMap<>();
        stepD.put("C", 8);
        stepD.put("E", 6);
        HashMap<String, Integer> stepE = new HashMap<>();
        stepE.put("B", 3);
        stepLength.put("A", stepA);
        stepLength.put("B", stepB);
        stepLength.put("C", stepC);
        stepLength.put("D", stepD);
        stepLength.put("E", stepE);
        return stepLength;
    }
}
