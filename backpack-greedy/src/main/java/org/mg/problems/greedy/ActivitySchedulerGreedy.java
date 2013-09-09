package org.mg.problems.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * John wants to have the time of his life! With so many things to do, it is
 * unfortunately impossible for him to enjoy them all. So, as soon as he eats
 * his breakfast, he devises a "Fun Plan" in which he describes a schedule of
 * his upcoming activities:
 * 
 * <pre>
 * 1 Debug the room Monday, 10:00 PM - Tuesday, 1:00 AM 
 * 2 Enjoy a trip to Hawaii Tuesday, 6:00 AM - Saturday, 10:00 PM 
 * 3 Win the Chess Championship Tuesday,11:00 AM - Tuesday, 9:00 PM 
 * 4 Attend the Rock Concert Tuesday, 7:00 PM - Tuesday, 11:00 PM 
 * 5 Win the Starcraft Tournament Wednesday, 3:00 PM - Thursday, 3:00 PM 
 * 6 Have some paintball fun Thursday, 10:00 AM - Thursday,4:00 PM 
 * 7 Participate in the TopCoder Single Round Match Saturday, 12:00 PM - Saturday, 2:00 PM 
 * 8 Take a shower Saturday, 8:30 PM - Saturday 8:45 PM 
 * 9 Organize a Slumber Party Saturday, 9:00 PM - Sunday, 6:00 AM 
 * 10 Participate in an "All you can eat" and "All you can drink" contest Saturday, 9:01 PM -Saturday, 11:59 PM
 * </pre>
 * 
 * He now wishes to take advantage of as many as he can. Such careful planning
 * requires some cleverness, but his mind has gone on vacation too. This is John
 * Smith's problem and he needs our help.
 * 
 * @author mga
 *  
 * @see http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=greedyAlg
 */
public class ActivitySchedulerGreedy {
	public static void main(String[] args) {
		Activity activity1 = new Activity("Debug the room", new Date(
				2013, 0, 1, 22, 0), new Date(2013, 0, 2, 1, 0));
		Activity activity2 = new Activity("Enjoy a trip to Hawaii", new Date(
				2013, 0, 2, 6, 0), new Date(2013, 0, 6, 22, 0));
		Activity activity3 = new Activity("Win the Chess Championship Tuesday",
				new Date(2013, 0, 2, 11, 0), new Date(2013, 0, 2, 21, 0));
		Activity activity4 = new Activity("Attend the Rock Concert", new Date(
				2013, 0, 2, 19, 0), new Date(2013, 0, 2, 23, 0));
		Activity activity5 = new Activity("Win the Starcraft Tournament ",
				new Date(2013, 0, 3, 15, 0), new Date(2013, 0, 4, 15, 0));
		Activity activity6 = new Activity("Have some paintball fun", new Date(
				2013, 0, 4, 10, 0), new Date(2013, 0, 4, 16, 0));
		Activity activity7 = new Activity(
				"Participate in the TopCoder Single Round Match", new Date(
						2013, 0, 6, 12, 0), new Date(2013, 0, 6, 14, 0));
		Activity activity8 = new Activity("Take a shower", new Date(2013, 0, 6,
				20, 30), new Date(2013, 0, 6, 20, 45));
		Activity activity9 = new Activity("Organize a Slumber Party", new Date(
				2013, 0, 6, 21, 0), new Date(2013, 0, 7, 6, 0));
		Activity activity10 = new Activity(
				"Participate in an \"All you can eat\" and \"All you can drink\"",
				new Date(2013, 0, 6, 21, 1), new Date(2013, 0, 6, 23, 59));
		
		
		List<Activity> activities =new ArrayList();
		activities.add(activity1);
		activities.add(activity2);
		activities.add(activity3);
		activities.add(activity4);
		activities.add(activity5);
		activities.add(activity6);
		activities.add(activity7);
		activities.add(activity8);
		activities.add(activity9);
		activities.add(activity10);
		
		// sort the activities after the finish time ascending
		Collections.sort(activities, new Comparator<Activity>() {

			public int compare(Activity a1, Activity a2) {
				return a1.getEndTime().compareTo(a2.getEndTime());
			}
			
		});
		
		
		
		List<Activity> chosenActivities = new ArrayList<Activity>();
		
		Iterator<Activity> iterator = activities.iterator();
		
		if (iterator.hasNext()){
			Activity lastChosenActivity = iterator.next();
			chosenActivities.add(lastChosenActivity);

			for (;iterator.hasNext();){
				Activity activity = iterator.next();
				
				if (activity.getStartTime().compareTo(lastChosenActivity.getEndTime()) >= 0){
					chosenActivities.add(activity);
					lastChosenActivity = activity;
				}
			}
			
		}
		
		for (Activity chosenActivity :chosenActivities){
			System.out.println(chosenActivity);
		}
	}
}

class Activity {
	private String name;
	private Date startTime;
	private Date endTime;

	public Activity(String name, Date startTime, Date endTime) {
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	@Override
	public String toString() {
		return "Activity [name=" + name + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
	
}
