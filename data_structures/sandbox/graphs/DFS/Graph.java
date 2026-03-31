package graphs.DFS;

import java.util.*;

public class Graph {
	private class Vertex {
		private enum Color {
			WHITE, GRAY, BLACK
		}

		private static int time = 0;
		private ArrayList<Vertex> neighbors = new ArrayList<>();
		private Color color;
		private int discoveryTime, finishingTime;
		Vertex parent;

		public Vertex() {
			reset();
		}

		private void reset() {
			color = Color.WHITE;
			discoveryTime = -1;
			finishingTime = -1;
			parent = null;
		} 

		private void DFS() {
			color = Color.GRAY;
			discoveryTime = time++;

			for (Vertex v : neighbors) {
				if (v.color == Color.WHITE) {
						v.parent = this;
						v.DFS();
				}
			}
			color = Color.BLACK;
			finishingTime = time++;
		}
}

	private ArrayList<Vertex> verticies = new ArrayList<>();

	private void DFS() {
		Vertex.time = 0;
		for (Vertex v : verticies) {
			if (v.color == Vertex.Color.WHITE) {
				v.DFS();
			}
		}
	}

	public static void Test1() {
		Graph g = new Graph();
		
		Vertex[] v = new Vertex[11];
		for (int i = 0; i < 11; i++) {
			v[i] = g.new Vertex();
			g.verticies.add(v[i]);
		}

		v[0].neighbors.add(v[1]);
		v[0].neighbors.add(v[2]);

		v[1].neighbors.add(v[2]);

		v[2].neighbors.add(v[3]);
		v[2].neighbors.add(v[4]);

		v[4].neighbors.add(v[5]);

		v[5].neighbors.add(v[6]);

		v[7].neighbors.add(v[8]);
		v[7].neighbors.add(v[9]);

		v[8].neighbors.add(v[6]);

		v[9].neighbors.add(v[10]);

		v[10].neighbors.add(v[4]);

		g.DFS();

		for (int i = 0; i < 11; i++) {
			System.out.println(i + " " + v[i].color.toString() + " " + v[i].discoveryTime + " " + v[i].finishingTime);
		}
	}
}
