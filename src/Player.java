import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable
{
    private String playerName;
    private int score, framesWon, totPoints, currentBreak, highestBreak;
    private int numPotted, numMiss, numSafeSucceed, numSafeFailed;
    private int numRed, numYellow, numGreen, numBrown, numBlue, numPink, numBlack;
    private boolean endGameStarted;
    private boolean[] endGame;
    private ArrayList<String> regretHistory;
    private ArrayList<Integer> regretValueHistory;

    public Player(String playerName)
    {
        this.playerName = playerName;
        framesWon = 0;
        totPoints = 0;
        numPotted = 0;
        numMiss = 0;
        numSafeSucceed = 0;
        numSafeFailed = 0;
        highestBreak = 0;
        endGame = new boolean[8];
        regretHistory = new ArrayList<String>();
        regretValueHistory = new ArrayList<Integer>();

        reset();
    }

    public String getName()
    {
        return playerName;
    }

    public int getHighestBreak()
    {
        return highestBreak;
    }

    public int getCurrentBreak()
    {
        return currentBreak;
    }

    public void resetBreak()
    {
        if (currentBreak > highestBreak)
            highestBreak = currentBreak;

        currentBreak = 0;
    }

    public int getFramesWon()
    {
        return framesWon;
    }

    public void addFrameWon()
    {
        framesWon++;
    }
    
    public void addMiss()
    {
        numMiss++;
        regretHistory.add("addMiss");
        regretValueHistory.add(0);
    }

    public void addSafeSucceed()
    {
        numSafeSucceed++;
        regretHistory.add("addSafeSucceed");
        regretValueHistory.add(0);
    }

    public void addSafeFail()
    {
        numSafeFailed++;
        regretHistory.add("addSafeFail");
        regretValueHistory.add(0);
    }

    public void addBall(int value)
    {
        switch (value)
        {
            case 1:
                numRed++;
                break;
            case 2:
                numYellow++;
                break;
            case 3:
                numGreen++;
                break;
            case 4:
                numBrown++;
                break;
            case 5:
                numBlue++;
                break;
            case 6:
                numPink++;
                break;
            case 7:
                numBlack++;
                break;
        }

        if (endGameStarted)
            endGame[value] = true;

        numPotted++;
        score += value;
        currentBreak += value;
        totPoints += value;

        regretHistory.add("addBall");
        regretValueHistory.add(value);
    }

    public void addScore(int scoreAdd)
    {
        score += scoreAdd;
        if(scoreAdd == 1)
        {
            totPoints += scoreAdd;
            numPotted++;
            currentBreak += scoreAdd;
        }

        regretHistory.add("addScore");
        regretValueHistory.add(scoreAdd);
    }

    public int getScore()
    {
        return score;
    }

    public int getNumBall(int value)
    {
        switch (value)
        {
            case 1: return numRed;
            case 2: return numYellow;
            case 3: return numGreen;
            case 4: return numBrown;
            case 5: return numBlue;
            case 6: return numPink;
            case 7: return numBlack;
            default: return 0;
        }
    }

    public boolean getLastBall(int value)
    {
        return endGame[value];
    }

    public int getTotPoints()
    {
        return totPoints;
    }

    public void startEndGame()
    {
        endGameStarted = true;
    }

    public void cueBallPocket()
    {
        int lastValue = regretValueHistory.get(regretValueHistory.size()-1);

        score -= lastValue;
        currentBreak -= lastValue;
        totPoints -= lastValue;

        regretHistory.add("cueBallPocket");
        regretValueHistory.add(lastValue);
    }

    public void reset()
    {
        score = 0;
        numRed = 0;
        numYellow = 0;
        numGreen = 0;
        numBrown = 0;
        numBlue = 0;
        numPink = 0;
        numBlack = 0;

        endGameStarted = false;

        java.util.Arrays.fill(endGame,false);


        if (currentBreak > highestBreak)
            highestBreak = currentBreak;

        currentBreak = 0;

    }

    public boolean regret()
    {
        String last = regretHistory.get(regretHistory.size()-1);
        int lastValue = regretValueHistory.get(regretValueHistory.size()-1);


        if (last.equals("addMiss"))
            numMiss--;

        else if (last.equals("addSafeSucceed"))
            numSafeSucceed--;

        else if (last.equals("addSafeFail"))
            numSafeFailed--;

        else if (last.equals("addScore"))
        {
            score -= lastValue;
            if(lastValue == 1)
            {
                totPoints -= lastValue;
                numPotted--;
            }
        }

        else if (last.equals("addBall"))
        {
            switch (lastValue)
            {
                case 1:
                    numRed--;
                    break;
                case 2:
                    numYellow--;
                    break;
                case 3:
                    numGreen--;
                    break;
                case 4:
                    numBrown--;
                    break;
                case 5:
                    numBlue--;
                    break;
                case 6:
                    numPink--;
                    break;
                case 7:
                    numBlack--;
                    break;
            }

            if (endGameStarted)
                endGame[lastValue] = false;

            numPotted--;
            score -= lastValue;
            totPoints -= lastValue;
        }

        else if (last.equals("cueBallPocket"))
        {
            score += lastValue;
            currentBreak += lastValue;
            totPoints += lastValue;
        }

        regretHistory.remove(regretHistory.size()-1);
        regretValueHistory.remove(regretValueHistory.size()-1);

        
        return !regretHistory.isEmpty();

    }

    public boolean removeHistory()
    {
        regretHistory.clear();
        regretValueHistory.clear();
        return false;
    }

    public int getNumPotted()
    {
        return numPotted;
    }

    public double getPotSuccess()
    {
        if (numPotted == 0 && numMiss == 0)
            return 0.0;
        else
            return ((double)numPotted / ((double)numPotted + (double)numMiss));
    }

    public double getSafetySuccess()
    {
        if (numSafeSucceed == 0 && numSafeFailed == 0)
            return 0.0;
        else
            return ((double)numSafeSucceed / ((double)numSafeSucceed + (double)numSafeFailed));
    }

    public ArrayList<String> getHistory()
    {
        return regretHistory; 
    }

    public ArrayList<Integer> getValueHistory()
    {
        return regretValueHistory;
    }

}