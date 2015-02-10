import javax.swing.*;
import java.awt.*;

public class MatchPanel extends JPanel
{
    private PlayerPanel p1panel, p2panel;
    private ScorePanel scorePanel;
    private StatPanel p1StatPanel, p2StatPanel;
    private HistoryPanel p1HistoryPanel, p2HistoryPanel;
    private JPanel center;

    public MatchPanel(int totNumRedBalls)
    {
        setPreferredSize(new Dimension(940, 530));
        setLayout(new BorderLayout());
        center = new JPanel();

        p1panel = new PlayerPanel(false, totNumRedBalls);
        p2panel = new PlayerPanel(true, totNumRedBalls);

        scorePanel = new ScorePanel(p1panel, p2panel, totNumRedBalls);

        p1StatPanel = new StatPanel(false, p1panel, p2panel);
        p2StatPanel = new StatPanel(true, p1panel, p2panel);

        p1HistoryPanel = new HistoryPanel(p1panel);
        p2HistoryPanel = new HistoryPanel(p2panel);

        p1panel.setup(p2panel, scorePanel, p1StatPanel, p1HistoryPanel);
        p2panel.setup(p1panel, scorePanel, p2StatPanel, p2HistoryPanel);

        center.add(p1panel);
        center.add(p2panel);
        center.add(p1StatPanel);
        center.add(scorePanel);
        center.add(p2StatPanel);

        add(p1HistoryPanel, BorderLayout.WEST);
        add(p2HistoryPanel, BorderLayout.EAST);
        add(center, BorderLayout.CENTER);
    }

}