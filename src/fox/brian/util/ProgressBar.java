/*
 * $Id: ProgressBar.java,v 1.3 2010/03/10 23:04:22 bfox Exp $
 * @Copyright@
 * @Copyright@ 
 * 
 * $Log: ProgressBar.java,v $
 * Revision 1.3  2010/03/10 23:04:22  bfox
 * Added cvs logging.
 *
 *
*/


package fox.brian.util;

public class ProgressBar {
	private boolean showtime = true;
	private boolean showperc = true;
	private boolean showbar = false;
	private boolean showmem = true;
	private boolean showcnt = true;

	private long upper = 1;
	private long current = 0;

	private PerformanceTimer timer;
	
	public ProgressBar(long lower, long upper) {
		timer = new PerformanceTimer();
		this.upper = upper;
	}

	public boolean getShowBar() {
		return showbar;
	}

	public boolean getShowPercentage() {
		return showperc;
	}

	public boolean getShowTime() {
		return showtime;
	}

	public void setShowBar(boolean show) {
		showbar = show;
	}

	public void setShowPercentage(boolean show) {
		showperc = show;
	}

	public void setShowTime(boolean show) {
		showtime = show;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (showtime) {
			float t = timer.millisecondsElapsed();
			int milliseconds = (int)t % 1000;  t /= 1000;
			int seconds = (int)t % 60;         t /= 60;
			int minutes = (int)t % 60;         t /= 60;
			int hours = (int)t % 24;           t /= 24; 
			int days = (int)t;
			if (seconds + minutes + hours + days == 0) {
				sb.append(String.format("%15s", String.format("     %3dms", milliseconds)));
			}
			else if (minutes + hours + days == 0) {
				sb.append(String.format("%15s", String.format("%2ds %3dms", seconds, milliseconds)));
			}
			else if (hours + days == 0) {
				sb.append(String.format("%15s", String.format("%2dm %2ds ", minutes, seconds)));
			}
			else if (days == 0) {
				sb.append(String.format("%15s", String.format("%2dh %2dm ", hours, minutes)));
			}
			else {
				sb.append(String.format("%15s", String.format("%2dd %2dh ", days, hours)));
			}
			sb.append("    ");
		}
		if (showperc) {
			sb.append(String.format("%8s",String.format("%2.1f%%", ((float)current/upper)*100)));
			sb.append("    ");
		}

		if (showcnt) {
			sb.append(String.format("%20s",String.format("%d/%d", current,upper)));
			sb.append("    ");
		}

		if (showmem) {
			long used = timer.memoryUsed();
			boolean pos = true;
			String s = String.format("%d", used);
			if (used < 0) {
				pos = false;
				used = -used;
			}
			if (used < 1024)
				s = String.format("%dB", used);
			else if (used < 1024*1024f)
				s = String.format("%.1fKB", used/(1024f));
			else if (used < 1024*1024*1024f)
				s = String.format("%.1fMB", used/(1024*1024f));
			else if (used < 1024*1024*1024*1024f)
				s = String.format("%.1fGB", used/(1024*1024*1024f));
			else if (used < 1024*1024*1024*1024*1024f)
				s = String.format("%.1fTB", used/(1024*1024*1024*1024f));
			else if (used < 1024*1024*1024*1024*1024*1024f)
				s = String.format("%.1fPB", used/(1024*1024*1024*1024*1024f));
			sb.append(pos ? " " : "-");
			sb.append(String.format("%12s  ",s));
		}

		return sb.toString();
	
	}
	
	public void setProgress(long p) {
		current = p;
	}
}
