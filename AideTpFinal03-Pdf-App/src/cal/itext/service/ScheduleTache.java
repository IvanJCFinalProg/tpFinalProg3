package cal.itext.service;

import java.util.Timer;

public class ScheduleTache {

	private Timer timer;
	
	public ScheduleTache(int delaiAttente, int frequence) {
		timer = new Timer();
		timer.schedule(new Tache(), delaiAttente, frequence);
	}
	
	public static void main(String[] args) throws Exception{
		new ScheduleTache(2000, 3000);
	}
}