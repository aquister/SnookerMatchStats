import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ScorePanel extends JPanel implements Runnable
{
    private JLabel pointsOnTable, matchTime;
    private JButton startEndGame;
    private PlayerPanel p1panel, p2panel;
    private Color bg;
    private Font font;
    private int totNumRedBalls;
    private long matchStartTime;
    private DateFormat df;

    public ScorePanel(PlayerPanel p1, PlayerPanel p2, int totNumRedBalls)
    {
        p1panel = p1;
        p2panel = p2;
        this.totNumRedBalls = totNumRedBalls;
        font = new Font("", 1, 20);
        bg = Color.PINK;
        matchStartTime = System.currentTimeMillis();
        df = new SimpleDateFormat("HH':'mm':'ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+0"));

        setBackground(bg);
        setPreferredSize(new Dimension(245, 160));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        startEndGame = new JButton("Start EndGame");
        startEndGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        startEndGame.addActionListener(new EndGameAction());

        pointsOnTable = new JLabel();
        pointsOnTable.setFont(font);
        pointsOnTable.setAlignmentX(Component.CENTER_ALIGNMENT);

        matchTime = new JLabel();
        matchTime.setFont(font);
        matchTime.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(matchTime);
        add(new JLabel(" "));
        add(startEndGame);
        add(new JLabel(" "));
        add(pointsOnTable);

        setRemPoints();

        (new Thread(this)).start();
    }

    public void run()
    {

        while (true)
        {
            long elapsed = System.currentTimeMillis() - matchStartTime;
            matchTime.setText("Match Time: " + df.format(new Date(elapsed)));
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException intrExce){}
        }
    }

    public void setRemPoints()
    {
         pointsOnTable.setText("Points on table: " + getRemPoints());
    }

    public int getRemPoints()
    {
        int numRedLeft = totNumRedBalls - (p1panel.getNumBall(1) + p2panel.getNumBall(1));

        if (numRedLeft > 0)
            return (8 * numRedLeft + 27);

        else
        {
            int points = 27;
            for (int i = 2; i <= 7; i++)
                if (p1panel.getLastBall(i) || p2panel.getLastBall(i))
                    points -= i;


            return points;
        }

    }

    public void enableEndGameButton()
    {
        startEndGame.setEnabled(true);
    }

    private class EndGameAction implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            p1panel.startEndGame();
            p2panel.startEndGame();

            startEndGame.setEnabled(false);
        }
    }

}
