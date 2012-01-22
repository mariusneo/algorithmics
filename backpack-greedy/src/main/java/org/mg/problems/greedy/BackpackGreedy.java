package org.mg.problems.greedy;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Solution for the classical backpack problem to be solved with
 * &quot;Greedy&quot; technique. Problem : Having a series of <i>n</i> objects,
 * where the object <i>i</i> has the value <i>v(i)</i> and the weight
 * <i>w(i)</i> find out the most valuable combination of objects which can be
 * fit into a backpack with a capacity of <i>W</i> units.
 * 
 * @author marius
 * 
 */
public class BackpackGreedy {

	public static void main(String[] args) {
		List<Ware> availableWares = new LinkedList<Ware>();
		availableWares.add(new Ware(10, 60));
		availableWares.add(new Ware(20, 100));
		availableWares.add(new Ware(30, 120));

		Comparator<Ware> decreasingWeightValueRationComparator = new Comparator<Ware>() {

			public int compare(Ware ware, Ware otherWare) {
				double ratioWare = ware.value / ware.weight;
				double ratioOtherWare = otherWare.value / otherWare.weight;
				return Double.compare(ratioOtherWare, ratioWare);
			}

		};
		Collections.sort(availableWares, decreasingWeightValueRationComparator);

		int backpackWeight = 50;
		int availableWeight = backpackWeight;
		List<Ware> selectedWares = new LinkedList<Ware>();
		for (Ware ware : availableWares) {
			if (availableWeight == 0) {
				break;
			}
			if (availableWeight - ware.weight >= 0) {
				selectedWares.add(ware);
				availableWeight -= ware.weight;
			}else{
				double partialValue = ware.value * (availableWeight/ware.weight);
				Ware partialWare = new Ware(availableWeight, partialValue);
				selectedWares.add(partialWare);
				availableWeight = 0;
			}
		}

		System.out.println("Selected wares: ");
		int selectedWareValue = 0;
		for (Ware ware : selectedWares) {
			System.out.println(ware);
			selectedWareValue += ware.value;
		}

		System.out.println();
		System.out.println("Total value: " + selectedWareValue);
		System.out.println("Weight not used " + availableWeight);
	}

	/**
	 * Ware to be introduced in the backpack.
	 * 
	 * @author marius
	 * 
	 */
	private static class Ware {
		private double weight;
		private double value;

		public Ware() {
		}

		public Ware(double weight, double value) {
			this.weight = weight;
			this.value = value;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		public double getValue() {
			return value;
		}

		public void setValue(double value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Ware [weight=" + weight + ", value=" + value + "]";
		}

	}
}
