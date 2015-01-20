package org.openntf.conference.graph;

import java.util.Date;

import org.openntf.conference.graph.Event.HappeningOn;
import org.openntf.domino.graph2.annotations.AdjacencyUnique;
import org.openntf.domino.graph2.annotations.IncidenceUnique;
import org.openntf.domino.graph2.annotations.TypedProperty;
import org.openntf.domino.graph2.builtin.DVertexFrame;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.modules.javahandler.JavaHandler;
import com.tinkerpop.frames.modules.javahandler.JavaHandlerClass;

@JavaHandlerClass(TimeSlot.TimeSlotImpl.class)
public interface TimeSlot extends DVertexFrame {
	public abstract static class TimeSlotImpl implements TimeSlot {
		@Override
		public Integer getDuration() {
			Integer result = Integer.valueOf(0);
			try {
				Date start = getStartTime();
				Date end = getEndTime();
				long msDifference = end.getTime() - start.getTime();
				result = Integer.valueOf(Long.valueOf(msDifference / 60000).intValue());
			} catch (Throwable t) {
				//TODO what?
			}
			return result;
		}
	}

	@TypedProperty("Starttime")
	public Date getStartTime();

	@TypedProperty("Starttime")
	public void setStartTime(Date startTime);

	@TypedProperty("Endtime")
	public Date getEndTime();

	@TypedProperty("Endtime")
	public void setEndTime(Date endTime);

	@JavaHandler
	public Integer getDuration();	//in minutes

	@AdjacencyUnique(label = HappeningOn.LABEL, direction = Direction.IN)
	public Iterable<Event> getEvents();

	@AdjacencyUnique(label = HappeningOn.LABEL, direction = Direction.IN)
	public HappeningOn addEvent(Event event);

	@AdjacencyUnique(label = HappeningOn.LABEL, direction = Direction.IN)
	public void removingEvent(Event event);

	@IncidenceUnique(label = HappeningOn.LABEL, direction = Direction.IN)
	public Iterable<HappeningOn> getHappeningOns();

	@IncidenceUnique(label = HappeningOn.LABEL, direction = Direction.IN)
	public void removingHappeningOn(HappeningOn happeningOn);

}