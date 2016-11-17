package threads;

import javax.swing.JLabel;

/**
 * A simple timer from Jeopardy project.  You can enter an {@code int}
 * in the constructor and the {@code Timer} class will give you
 * a label that contains a digital clock view of a countdown from
 * the given {@code int}.
 * @author alancoon
 *
 */
public class Timer extends Thread {

	public final int number_of_frames;
	private int current;
	private JLabel label;
	
	public Timer( int time) {
		this.label = new JLabel();
		this.number_of_frames = time;
		this.current = time;
		String seconds = (time < 10) ? "0" + time : "" + time; 
		this.label.setText("00:" + seconds);
		this.start();
	}

	
	@Override
	public void run() {
		for ( ; current >= 0; current--) {
			try {
				String seconds = (current < 10) ? "0" + current : "" + current;
				this.label.setText("00:" + seconds);
				label.revalidate();
				label.repaint();
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}
	
	public JLabel getLabel() { 
		return this.label;
	}
}
