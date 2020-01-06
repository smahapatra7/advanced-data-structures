package shibani.risingCity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class WayneEnterprises {

	private static long time = 0;
	private static int index = 0;
	private static Building selectedBuilding;

	public static boolean insert(MinHeap minHeap, RedBlackTree rbt, Building building) {
		if (!rbt.add(building)) {
			return false;
		}
		minHeap.insert(building);
		return true;
	}

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		boolean ini = false;
		MinHeap minHeap = new MinHeap(2000);
		RedBlackTree rbt = new RedBlackTree();
		String file = "src/input.txt";
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<String> timeList = new ArrayList<>();
		List<String> operation = new ArrayList<>();

		while ((line = reader.readLine()) != null) {
			timeList.add(line.split(":")[0]);
			operation.add(line.split(":")[1]);
		}
		reader.close();

		int d = 0;
		while (true) {

			if (ini && minHeap.getSize() == 0 && index >= timeList.size()) {
				break;
			}

			if (!ini) {
				performOperation(minHeap, rbt, timeList, operation);
				ini = true;
				selectedBuilding = minHeap.getMin();
				d = 0;
			} else {
				performOperation(minHeap, rbt, timeList, operation);
				if(minHeap.getSize()==1) {
					selectedBuilding = minHeap.getMin();
				}
			}
			time++;

			if (d == 0) {
				minHeap.minHeaps();
				selectedBuilding = minHeap.getMin();
			}
			// Work on the building
			
			if (selectedBuilding != null) {
			d++;
				selectedBuilding.setExecutedTime(selectedBuilding.getExecutedTime() + 1);
				if (selectedBuilding.getExecutedTime() == selectedBuilding.getTotalTime()) {
					print(selectedBuilding, time);
					minHeap.remove();
					rbt.remove(selectedBuilding);
					minHeap.minHeaps();
					selectedBuilding = minHeap.getMin();
					d = 0;
				}
			}

			if (d == 5) {
				minHeap.minHeaps();
				selectedBuilding = minHeap.getMin();
				d = 0;
			}
		}

		System.out.println("Time Taken: ");
		System.out.println(System.currentTimeMillis() - start);

	}

	private static void performOperation(MinHeap minHeap, RedBlackTree rbt, List<String> timeList,
			List<String> operation) {
		if ((index < timeList.size()) && time == Integer.parseInt(timeList.get(index))) {
			// doOperation
			if (operation.get(index).contains("Insert")) {
				String buildingNo = operation.get(index).substring(operation.get(index).indexOf("(") + 1,
						operation.get(index).indexOf(","));
				String totalTime = operation.get(index).substring(operation.get(index).indexOf(",") + 1,
						operation.get(index).indexOf(")"));
				if (!insert(minHeap, rbt, new Building(Integer.parseInt(buildingNo), 0, Integer.parseInt(totalTime)))) {
					return;
				}
			} else if (operation.get(index).contains("Print")) {
				String range = operation.get(index).substring(operation.get(index).indexOf("(") + 1,
						operation.get(index).indexOf(")"));
				if (range.contains(",")) {
					ArrayList<Building> list = RedBlackTree.searchRange(new ArrayList<>(), RedBlackTree.root,
							Integer.parseInt(range.split(",")[0]), Integer.parseInt(range.split(",")[1]));
					if(list.isEmpty()) {
						System.out.println("(0,0,0)");
					}else {
						StringBuilder result = new StringBuilder();
						for (Building building : list) {
							result.append("(");
							result.append(building.getBuildingNum());
							result.append(",");
							result.append(building.getExecutedTime());
							result.append(",");
							result.append(building.getTotalTime());
							result.append(")");
							result.append(",");
						}
						System.out.println(result.substring(0, result.length() - 1));
					}
				} else {
					Building building = RedBlackTree.search(RedBlackTree.root, Integer.parseInt(range));
					if (building == null) {
						System.out.println("(0,0,0)");
					} else {
						System.out.println("(" + building.getBuildingNum() + "," + building.getExecutedTime() + "," + building.getTotalTime()+")");
					}
				}
			}
			index++;
		}

	}

	private static void print(Building building, long time) {
		System.out.println("(" + building.getBuildingNum() + "," + time + ")");
	}

}
