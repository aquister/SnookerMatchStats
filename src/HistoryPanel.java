import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistoryPanel extends JPanel
{
    private PlayerPanel playerPanel;
    private JTextArea history;

    public HistoryPanel(PlayerPanel playerPanel)
    {
        this.playerPanel = playerPanel;
        setPreferredSize(new Dimension(100, 0));

        history = new JTextArea(30,9);
        history.setEditable(false);
        history.setBackground(Color.getColor("opaque"));
        add(history);
    }

    public void update()
    {
        ArrayList<String> his = playerPanel.getHistory();
        ArrayList<Integer> hisValue = playerPanel.getValueHistory();

        history.setText(null);

        for (int i = his.size()-1; i >= 0; i--)
        {
            if(his.get(i).equals("addBall"))
            {
                switch (hisValue.get(i))
                {
                    case 1:
                        history.append("Red");
                        break;
                    case 2:
                        history.append("Yellow");
                        break;
                    case 3:
                        history.append("Green");
                        break;
                    case 4:
                        history.append("Brown");
                        break;
                    case 5:
                        history.append("Blue");
                        break;
                    case 6:
                        history.append("Pink");
                        break;
                    case 7:
                        history.append("Black");
                        break;
                }
            }
            else if (his.get(i).equals("addMiss"))
                history.append("Miss");

            else if (his.get(i).equals("addSafeSucceed"))
                history.append("Succeeded Safe");

            else if (his.get(i).equals("addSafeFail"))
                history.append("Failed Safe");

            else if (his.get(i).equals("addScore"))
                history.append("Added Score: " + hisValue.get(i));

            else if (his.get(i).equals("cueBallPocket"))
                history.append("Pocket Cue Ball: " + hisValue.get(i));

            
            history.append("\n");
        }
        
    }

}
