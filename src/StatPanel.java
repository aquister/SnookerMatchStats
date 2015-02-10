import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class StatPanel extends JPanel
{
    private JLabel difference, totPoints, potSuccess, safeSuccess, highestBreak, numPotted;
    private Color bg;
    private Font font;
    private PlayerPanel p1panel, p2panel;
    private boolean second;

    public StatPanel(boolean second, PlayerPanel p1panel, PlayerPanel p2panel)
    {
        this.second = second;
        this.p1panel = p1panel;
        this.p2panel = p2panel;

        font = new Font("", 1, 15);
        if (second) bg = Color.CYAN;
        else bg = Color.LIGHT_GRAY;

        setBackground(bg);
        setPreferredSize(new Dimension(235, 160));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        difference = new JLabel();
        totPoints = new JLabel();
        potSuccess = new JLabel();
        safeSuccess = new JLabel();
        highestBreak = new JLabel();
        numPotted = new JLabel();

        difference.setFont(font);
        totPoints.setFont(font);
        potSuccess.setFont(font);
        safeSuccess.setFont(font);
        highestBreak.setFont(font);
        numPotted.setFont(font);

        difference.setAlignmentX(Component.CENTER_ALIGNMENT);
        totPoints.setAlignmentX(Component.CENTER_ALIGNMENT);
        potSuccess.setAlignmentX(Component.CENTER_ALIGNMENT);
        safeSuccess.setAlignmentX(Component.CENTER_ALIGNMENT);
        highestBreak.setAlignmentX(Component.CENTER_ALIGNMENT);
        numPotted.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(difference);
        add(new JLabel(" "));
        add(potSuccess);
        //add(safeSuccess);
        add(highestBreak);
        add(numPotted);
        add(totPoints);

        update();
    }
    
    public void update()
    {
        setDiff();
        setTotPoints();
        setSuccess();
        setHighestBreak();
        setNumPotted();
    }

    private void setDiff()
    {
        int p1 = p1panel.getScore();
        int p2 = p2panel.getScore();

        if (p1 > p2)
            difference.setText((p1 - p2) + (second ? " BEHIND" : " AHEAD"));

        else if (p1 == p2)
            difference.setText("0 AHEAD");

        else
            difference.setText((p2 - p1) + (second ? " AHEAD" : " BEHIND"));
    }

    private void setTotPoints()
    {
        totPoints.setText("Total points: " + (second ? p2panel.getTotPoints() : p1panel.getTotPoints()));
    }

    private void setSuccess()
    {
        DecimalFormat fmt = new DecimalFormat("#0.0%");

        if (second)
        {
            potSuccess.setText("Pot Success: " + fmt.format(p2panel.getPotSuccess()));
            safeSuccess.setText("Safety Success: " + fmt.format(p2panel.getSafetySuccess()));
        }
        else
        {
            potSuccess.setText("Pot Success: " + fmt.format(p1panel.getPotSuccess()));
            safeSuccess.setText("Safety Success: " + fmt.format(p1panel.getSafetySuccess()));
        }
    }

    private void setHighestBreak()
    {
        highestBreak.setText("Highest Break: " + (second ? p2panel.getHighestBreak() : p1panel.getHighestBreak()));
    }

    private void setNumPotted()
    {
        numPotted.setText("Balls Potted: " + (second ? p2panel.getNumPotted() : p1panel.getNumPotted()));
    }

}
