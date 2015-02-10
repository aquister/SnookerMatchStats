import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlayerPanel extends JPanel implements ActionListener
{
    private Border empty;
    private Font buttonFont;
    private Color buttonFontColor, bg;
    private Player player;
    private JButton red, yellow, green, brown, blue, pink, black, addOne, addFour,
            addFive, addSix, addSeven, addMiss, addSafe, addSafeFail, regret, cueBallPocket;
    private JLabel score, frames, labelName, breakLabel;
    private PlayerPanel otherPlayerPanel;
    private ScorePanel scorePanel;
    private StatPanel statPanel;
    private HistoryPanel historyPanel;
    //private int totNumRedBalls;

    public PlayerPanel(boolean second, int totNumRedBalls)
    {
        //this.totNumRedBalls = totNumRedBalls;
        String playerName = JOptionPane.showInputDialog(null, "Name of player: ", "Name", JOptionPane.QUESTION_MESSAGE).toUpperCase();
        if (second) bg = Color.CYAN;
        else bg = Color.LIGHT_GRAY;

        JPanel top = new JPanel();
        JPanel balls = new JPanel(new GridLayout(2, 4, 5, 5));
        JPanel cusPoints = new JPanel();
        JPanel statsButtons = new JPanel();
        JPanel framePanel = new JPanel();
        JPanel breakPanel = new JPanel();

        setPreferredSize (new Dimension(360, 350));
        top.setPreferredSize(new Dimension(300, 35));
        breakPanel.setPreferredSize(new Dimension(300, 25));
        setBackground(bg);
        top.setBackground(bg);
        balls.setBackground(bg);
        cusPoints.setBackground(bg);
        statsButtons.setBackground(bg);
        framePanel.setBackground(bg);
        breakPanel.setBackground(bg);

        player = new Player(playerName);
        empty = BorderFactory.createEmptyBorder();
        buttonFont = new Font("", 1, 35);
        buttonFontColor = Color.WHITE;
        frames = new JLabel("(0)");
        frames.setFont(buttonFont);
        score = new JLabel("0");
        score.setFont(buttonFont);
        labelName = new JLabel(player.getName());
        labelName.setFont(new Font("", 1, 20));
        breakLabel = new JLabel("");
        JButton winFrame = new JButton("Frame Won");
        winFrame.addActionListener(this);

        buttonSetup();

        //------------------------------------
        // Top Panel
        //------------------------------------
        top.add(labelName);

        //------------------------------------
        // Balls Panel
        //------------------------------------
        balls.add(red);
        balls.add(yellow);
        balls.add(green);
        balls.add(brown);
        balls.add(blue);
        balls.add(pink);
        balls.add(black);

        //------------------------------------
        // Custom points Panel
        //------------------------------------
        cusPoints.add(addOne);
        cusPoints.add(addFour);
        cusPoints.add(addFive);
        cusPoints.add(addSix);
        cusPoints.add(addSeven);

        //------------------------------------
        // Stats buttons Panel
        //------------------------------------
        statsButtons.add(addMiss);
        //statsButtons.add(addSafe);
        //statsButtons.add(addSafeFail);
        statsButtons.add(cueBallPocket);
        statsButtons.add(regret);   

        //------------------------------------
        // Frame Panel
        //------------------------------------
        if(second)
        {
            framePanel.add(frames);
            framePanel.add(new JLabel("                 "));
            framePanel.add(score);
            framePanel.add(new JLabel("                 "));
        }

        framePanel.add(winFrame);

        if(!second)
        {
            framePanel.add(new JLabel("                 "));
            framePanel.add(score);
            framePanel.add(new JLabel("                 "));
            framePanel.add(frames);
        }


        //------------------------------------
        // Break Panel
        //------------------------------------
        breakPanel.add(breakLabel);

        //------------------------------------
        // Main Panel
        //------------------------------------
        add(top);
        add(balls);
        add(cusPoints);
        add(statsButtons);
        add(framePanel);
        add(breakPanel);
    }

    public void setup(PlayerPanel otherPlPanel, ScorePanel scPanel, StatPanel stPanel, HistoryPanel hisPanel)
    {
        otherPlayerPanel = otherPlPanel;
        scorePanel = scPanel;
        statPanel = stPanel;
        historyPanel = hisPanel;
    }

    public void actionPerformed(ActionEvent ae)
    {
        regret.setEnabled(true);
        String action = ae.getActionCommand();

        if (action.equals("Frame Won"))
        {
            player.addFrameWon();
            reset();
            otherPlayerPanel.reset();
            scorePanel.enableEndGameButton();
        }

        else if (action.equals("miss"))
        {
            player.addMiss();
            cueBallPocket.setEnabled(false);
        }

        else if  (action.equals("safe"))
        {
            player.addSafeSucceed();
            cueBallPocket.setEnabled(false);
        }

        else if (action.equals("safeFail"))
        {
            player.addSafeFail();
            cueBallPocket.setEnabled(false);
        }

        else if (action.equals("regret"))
        {
            regret.setEnabled(player.regret());
            cueBallPocket.setEnabled(false);
        }

        else if (action.equals("cueBallPocket"))
        {
            player.cueBallPocket();
            cueBallPocket.setEnabled(false);
        }

        else if (action.startsWith("+"))
        {
            player.addScore(Integer.parseInt(action.substring(1)));
            cueBallPocket.setEnabled(false);
        }

        else
        {
            player.addBall(Integer.parseInt(action));
            cueBallPocket.setEnabled(true);
        }

        if (!action.equals("regret"))
            otherPlayerPanel.resetBreak();

        //checkStartEndGame();
        update();
        otherPlayerPanel.update();
    }

    /*
    private void checkStartEndGame()
    {
        int numRed = getNumBall(1) + otherPlayerPanel.getNumBall(1);

        int lastBall;

        if (player.getValueHistory().isEmpty())
            lastBall = 0;
        else
            lastBall = player.getValueHistory().get(player.getValueHistory().size()-1);

        if (numRed == totNumRedBalls && lastBall != 1)
        {
            startEndGame();
            otherPlayerPanel.startEndGame();
        }
    }
    */

    public void reset()
    {
        player.reset();
        regret.setEnabled(player.removeHistory());
        cueBallPocket.setEnabled(false);
    }

    public void update()
    {
        score.setText(Integer.toString(player.getScore()));

        updateBreak();

        red.setText(Integer.toString(player.getNumBall(1)));
        yellow.setText(Integer.toString(player.getNumBall(2)));
        green.setText(Integer.toString(player.getNumBall(3)));
        brown.setText(Integer.toString(player.getNumBall(4)));
        blue.setText(Integer.toString(player.getNumBall(5)));
        pink.setText(Integer.toString(player.getNumBall(6)));
        black.setText(Integer.toString(player.getNumBall(7)));

        frames.setText("(" + Integer.toString(player.getFramesWon()) + ")");

        scorePanel.setRemPoints();
        statPanel.update();
        historyPanel.update();
    }

    private void updateBreak()
    {
        int lastBall;

        if (player.getValueHistory().isEmpty())
            lastBall = 0;
        else
            lastBall = player.getValueHistory().get(player.getValueHistory().size()-1);

        int currBreak = player.getCurrentBreak();

        if (currBreak > 0)
            breakLabel.setText("Current break: " + currBreak + "   Possible break: "
                    + ((lastBall == 1) ? (currBreak + scorePanel.getRemPoints() + 7) : (currBreak + scorePanel.getRemPoints())));
        else
            breakLabel.setText("");
    }

    public int getNumBall(int value)
    {
        return player.getNumBall(value);
    }

    public int getScore()
    {
        return player.getScore();
    }

    public void startEndGame()
    {
        player.startEndGame();
    }

    public boolean getLastBall(int value)
    {
        return player.getLastBall(value);
    }

    public int getTotPoints()
    {
        return player.getTotPoints();
    }

    public int getNumPotted()
    {
        return player.getNumPotted();
    }

    public double getSafetySuccess()
    {
        return player.getSafetySuccess();
    }

    public double getPotSuccess()
    {
        return player.getPotSuccess();
    }

    public ArrayList<String> getHistory()
    {
        return player.getHistory();
    }

    public ArrayList<Integer> getValueHistory()
    {
        return player.getValueHistory();
    }

    public void resetBreak()
    {
        player.resetBreak();
    }

    public int getHighestBreak()
    {
        return player.getHighestBreak();
    }

    private void buttonSetup()
    {
        red = new JButton(new ImageIcon(this.getClass().getResource("/pic/red_ball.png")));
        yellow = new JButton(new ImageIcon(this.getClass().getResource("/pic/yellow_ball.png")));
        green = new JButton(new ImageIcon(this.getClass().getResource("/pic/green_ball.png")));
        brown = new JButton(new ImageIcon(this.getClass().getResource("/pic/brown_ball.png")));
        blue = new JButton(new ImageIcon(this.getClass().getResource("/pic/blue_ball.png")));
        pink = new JButton(new ImageIcon(this.getClass().getResource("/pic/pink_ball.png")));
        black = new JButton(new ImageIcon(this.getClass().getResource("/pic/black_ball.png")));

        red.setFont(buttonFont);
        yellow.setFont(buttonFont);
        green.setFont(buttonFont);
        brown.setFont(buttonFont);
        blue.setFont(buttonFont);
        pink.setFont(buttonFont);
        black.setFont(buttonFont);

        red.setForeground(buttonFontColor);
        yellow.setForeground(buttonFontColor);
        green.setForeground(buttonFontColor);
        brown.setForeground(buttonFontColor);
        blue.setForeground(buttonFontColor);
        pink.setForeground(buttonFontColor);
        black.setForeground(buttonFontColor);

        red.setBackground(Color.getColor("opaque"));
        yellow.setBackground(Color.getColor("opaque"));
        green.setBackground(Color.getColor("opaque"));
        brown.setBackground(Color.getColor("opaque"));
        blue.setBackground(Color.getColor("opaque"));
        pink.setBackground(Color.getColor("opaque"));
        black.setBackground(Color.getColor("opaque"));

        red.setBorder(empty);
        yellow.setBorder(empty);
        green.setBorder(empty);
        brown.setBorder(empty);
        blue.setBorder(empty);
        pink.setBorder(empty);
        black.setBorder(empty);

        red.setHorizontalTextPosition(SwingConstants.CENTER);
        yellow.setHorizontalTextPosition(SwingConstants.CENTER);
        green.setHorizontalTextPosition(SwingConstants.CENTER);
        brown.setHorizontalTextPosition(SwingConstants.CENTER);
        blue.setHorizontalTextPosition(SwingConstants.CENTER);
        pink.setHorizontalTextPosition(SwingConstants.CENTER);
        black.setHorizontalTextPosition(SwingConstants.CENTER);

        red.addActionListener(this);
        yellow.addActionListener(this);
        green.addActionListener(this);
        brown.addActionListener(this);
        blue.addActionListener(this);
        pink.addActionListener(this);
        black.addActionListener(this);

        red.setActionCommand("1");
        yellow.setActionCommand("2");
        green.setActionCommand("3");
        brown.setActionCommand("4");
        blue.setActionCommand("5");
        pink.setActionCommand("6");
        black.setActionCommand("7");

        //--------------------------------

        addOne = new JButton("+1");
        addFour = new JButton("+4");
        addFive = new JButton("+5");
        addSix = new JButton("+6");
        addSeven = new JButton("+7");

        addOne.setActionCommand("+1");
        addFour.setActionCommand("+4");
        addFive.setActionCommand("+5");
        addSix.setActionCommand("+6");
        addSeven.setActionCommand("+7");

        addOne.addActionListener(this);
        addFour.addActionListener(this);
        addFive.addActionListener(this);
        addSix.addActionListener(this);
        addSeven.addActionListener(this);

        //--------------------------------

        addMiss = new JButton("Miss");
        addSafe = new JButton("Safe");
        addSafeFail = new JButton("Failed Safe");
        regret = new JButton("Undo");
        cueBallPocket = new JButton("Pocket Cue Ball");

        addMiss.setActionCommand("miss");
        addSafe.setActionCommand("safe");
        addSafeFail.setActionCommand("safeFail");
        regret.setActionCommand("regret");
        cueBallPocket.setActionCommand("cueBallPocket");

        addMiss.addActionListener(this);
        addSafe.addActionListener(this);
        addSafeFail.addActionListener(this);
        regret.addActionListener(this);
        cueBallPocket.addActionListener(this);

        regret.setEnabled(false);
        cueBallPocket.setEnabled(false);
    }

}