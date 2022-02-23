/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinesweeperUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/*
Programmer: Evan Desjardine
Date: May 22nd, 2019
Program Name: Minesweeper-v2
Program Description: This program is a re-creation of the classic game
minesweeper. It has all the functions of the original 9x9 grid game, where the 
user must find the location of 10 mines by flipping tiles, which reveals the
number of mines touching the flipped tile, or a mine itself. The game is won by
flipping all the tiles that do not hide a mine.
*/

public class MainScreenUI extends javax.swing.JFrame implements ActionListener {

    boolean gameWon; //set to true when game has been won, removing abilities to click buttons
    long startTime, endTime; //used to calculate time taken to find all mines
    double gameTime; //used as score
    int height = 9; //dimensions of the minesweeper grid
    int width = 9;
    int tilesFlipped = 0; //keeps track of the number of tiles flipped
    int totalMines = 10; //how many mines are placed in the game
    int minesFlagged = 0; //used if game is lost to check how many mines had been found
    String[] scoreboard = createScoreboardArray(); //holds scoreboard information from xml file
    JButton[][] butArray = new JButton[height][width]; //holds all the buttons used
    int[][] numArray = new int[height][width]; //holds the numerical data for the minefield
    
    //Image Icons for each of the different PNGs made for the classic look
    ImageIcon zero = new ImageIcon(getClass().getResource("/MinesweeperUI/Tile-0.png"));
    ImageIcon one = new ImageIcon(getClass().getResource("/MinesweeperUI/Tile-1.png"));
    ImageIcon two = new ImageIcon(getClass().getResource("/MinesweeperUI/Tile-2.png"));
    ImageIcon three = new ImageIcon(getClass().getResource("/MinesweeperUI/Tile-3.png"));
    ImageIcon four = new ImageIcon(getClass().getResource("/MinesweeperUI/Tile-4.png"));
    ImageIcon five = new ImageIcon(getClass().getResource("/MinesweeperUI/Tile-5.png"));
    ImageIcon six = new ImageIcon(getClass().getResource("/MinesweeperUI/Tile-6.png"));
    ImageIcon seven = new ImageIcon(getClass().getResource("/MinesweeperUI/Tile-7.png"));
    ImageIcon eight = new ImageIcon(getClass().getResource("/MinesweeperUI/Tile-8.png"));
    ImageIcon basic = new ImageIcon(getClass().getResource("/MinesweeperUI/Basic-Tile.png"));
    ImageIcon flag = new ImageIcon(getClass().getResource("/MinesweeperUI/Flag-Tile.png"));
    ImageIcon wrongFlag = new ImageIcon(getClass().getResource("/MinesweeperUI/Wrong-Flag-Tile.png"));
    ImageIcon mine = new ImageIcon(getClass().getResource("/MinesweeperUI/Mine-Tile.png"));

    public MainScreenUI() {
        initComponents(); //initialize the GUI
        checkScoreExist(); //check that scores.xml exists, if not create a default form
        initializeButtons(); //create the button array, and format them all
        insertMines(totalMines); //add the mines (-1) to the numArray in random places
        setNeighbourValues(); //check each square to see how many mines are touching it, add that value to the correct array index
        instructScreen.setModal(true); //instruct screen must be addressed before moving on
        instructScreen.setVisible(true); //show the instructions to start
        reset(); //ensure all aspects of the game are set back to playable
    }

    private void checkScoreExist() { //checks if scores.xml exists, if not then creates a file with generic info
        (new File ("C:\\Minesweeper_v2")).mkdirs();
        File scores = new File("C:\\Minesweeper_v2\\scores.xml"); //This section is specific to my computer, would need to be changed for each new user
        boolean exists = scores.exists(); //check if the score file exists already
        if (exists == false) { //if it does not exist, create a default score file
            try {
                OutputStream fout = new FileOutputStream("C:\\Minesweeper_v2\\scores.xml");
                OutputStream bout = new BufferedOutputStream(fout);
                try (OutputStreamWriter out = new OutputStreamWriter(bout, "8859_1")) {
                    //add 5 places to the scoreboard
                    out.write("<scoreboard>\r\n");

                    //first place
                    out.write("<first>\r\n");
                    out.write("<name>Unknown</name>\r\n");
                    out.write("<time>999999</time>\r\n");
                    out.write("</first>\r\n");

                    //second place
                    out.write("<second>\r\n");
                    out.write("<name>Unknown</name>\r\n");
                    out.write("<time>999999</time>\r\n");
                    out.write("</second>\r\n");

                    //third place
                    out.write("<third>\r\n");
                    out.write("<name>Unknown</name>\r\n");
                    out.write("<time>999999</time>\r\n");
                    out.write("</third>\r\n");

                    //fourth place
                    out.write("<fourth>\r\n");
                    out.write("<name>Unknown</name>\r\n");
                    out.write("<time>999999</time>\r\n");
                    out.write("</fourth>\r\n");

                    //fifth place
                    out.write("<fifth>\r\n");
                    out.write("<name>Unknown</name>\r\n");
                    out.write("<time>999999</time>\r\n");
                    out.write("</fifth>\r\n");

                    //sixth place
                    out.write("<sixth>\r\n");
                    out.write("<name>Unknown</name>\r\n");
                    out.write("<time>999999</time>\r\n");
                    out.write("</sixth>\r\n");
                    
                    //seventh place
                    out.write("<seventh>\r\n");
                    out.write("<name>Unknown</name>\r\n");
                    out.write("<time>999999</time>\r\n");
                    out.write("</seventh>\r\n");
                    
                    //eighth place
                    out.write("<eighth>\r\n");
                    out.write("<name>Unknown</name>\r\n");
                    out.write("<time>999999</time>\r\n");
                    out.write("</eighth>\r\n");
                    
                    //ninth place
                    out.write("<ninth>\r\n");
                    out.write("<name>Unknown</name>\r\n");
                    out.write("<time>999999</time>\r\n");
                    out.write("</ninth>\r\n");
                    
                    //tenth place
                    out.write("<tenth>\r\n");
                    out.write("<name>Unknown</name>\r\n");
                    out.write("<time>999999</time>\r\n");
                    out.write("</tenth>\r\n");

                    out.write("</scoreboard> \r\n");
                    out.flush();  // Don't forget to flush!
                    
                    System.out.println("New File Made");
                }
            }
            catch (UnsupportedEncodingException e) {
                System.out.println(e.getMessage());
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String[] createScoreboardArray() { //returns an array with the scoreboard information from scores.xml
        String[] scoresData = new String[20]; //array to hold scoreboard contents, 10 spaces for scores, 10 for names
        try {
            String filepath = "C:\\Minesweeper_v2\\scores.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new FileInputStream(filepath));
            for (int i = 0; i < 10; i++) { //insert score.xml file info into scoreboard array
                Node name = doc.getElementsByTagName("name").item(i);
                Node time = doc.getElementsByTagName("time").item(i);
                scoresData[i * 2] = name.getTextContent(); //names at even indexes 0,2,4,6...
                scoresData[i * 2 + 1] = time.getTextContent(); //scores at odd indexes 1,3,5,7...
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException | TransformerException | IOException | SAXException pce) {
        }
        return scoresData;
    }

    private void initializeButtons() { //fills the button array with as many buttons as height * width
        for (int r = 0; r < height; r++) { 
            for (int c = 0; c < width; c++) {
                butArray[r][c] = new JButton(); //makes a new button
                butArray[r][c].setIcon(basic); //sets back to the basic icon
                butArray[r][c].addActionListener(this); //adds listener for being clicked
                gamePanel.add(butArray[r][c]); //button is added to the game panel
            }
            validate();
        }
    }

    private void insertMines(int amount) { //inserts mines into the field
        int[][] checkArray = new int[height][width]; //duplicate array to ensure two mines are not placed in the same space
        while (amount > 0) {
            int i = (int) Math.round(Math.abs(Math.random() * height - 1));
            int j = (int) Math.round(Math.abs(Math.random() * width - 1));
            if (checkArray[i][j] != -1) { //if there isn't a mine in checkArray[i][j] there is not one in numArray either
                numArray[i][j] = -1; //add mine to both arrays at [i][j]
                checkArray[i][j] = -1;
                amount -= 1; //one less mine to add
            }
        }
    }

    private void setNeighbourValues() {
        int mineCount = 0; //mineCount begins at 0
        for (int i = 0; i <= height - 1; i++) {
            for (int j = 0; j <= width - 1; j++) {
                if (numArray[i][j] != -1) { //if the selected cell is not a mine
                    for (int H = i - 1; H <= i + 1; H++) { //check all around the height one above and one above
                        if (H < 0 || H > height - 1) { //if the index will be less than 0 or more than height, skip that section of the for loop
                            continue;
                        }
                        for (int W = j - 1; W <= j + 1; W++) {
                            if (W < 0 || W > width - 1) { //if W index will be less than 0 or more than width don't check
                            }
                            else if (numArray[H][W] == -1) { //if there is a mine around the tile, add 1 to minecount
                                mineCount += 1;
                            }
                        }
                    }
                    numArray[i][j] = mineCount; //once all valid tiles around numArray[i][j] have been checked for mines, add the total number of mines to the array
                    mineCount = 0; //reset minecount and continue for each tile
                }
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(numArray[y][x]);
            }
            System.out.println();
        }
    }

    private void reset() { //resets variables, buttons and outputs before a game begins
        gameWon = false; //game has not been won yet
        flagButton.setEnabled(true); //make sure flag button is enabled
        tilesFlipped = 0; //tiles flipped is reset
        minesFlagged = 0; //no mines have been flagged
        infoOutput.setText("Your game has begun!"); //prompting text
        startTime = System.currentTimeMillis(); //beginning of game timer
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int r = 0; r < height; r++) { //check all the rows
            for (int c = 0; c < width; c++) { //in each row check all the columns
                if (ae.getSource() == butArray[r][c]) { //if the action event happened at this button, run the code for that one
                    if (gameWon != true) { //if game has not been won yet
                        boolean unflipped = (butArray[r][c].getIcon()).equals(basic); //tile is unflipped if the basic icon is still showing
                        boolean flagState = (butArray[r][c].getIcon()).equals(flag); //tile has a flag is the flag icon is showing
                        if (flagButton.isSelected() == true) { //if flagbutton is selected
                            if (flagState == true) { //if there's a flag on a tile, remove the flag
                                butArray[r][c].setIcon(basic);
                                if (numArray[r][c] == -1) {
                                    minesFlagged -= 1;  //if there had been a mine under the flagged tile, minesFlagged goes down 1
                                }
                            }
                            if (flagState == false && unflipped == true) { 
                                butArray[r][c].setIcon(flag); //if there was not a flag on the tile, and it is still unflipped, add a flag
                                flagState = true; //now there is a flag here
                                if (numArray[r][c] == -1) {
                                    minesFlagged += 1; //if there's a mine on the tile that is flagged, increase minesFlagged by one
                                }
                            }
                        }
                        if (flagState == false && unflipped == true) { //if there's no flag, and the tile has not been flipped yet then turn it over
                            if (numArray[r][c] != -1) { //if there's no mine, run individual cases by the number in the array associated with the button
                                switch (numArray[r][c]) { //set each tile to the proper icon based on the case number
                                    case 0:
                                        butArray[r][c].setIcon(zero);
                                        for (int H = r - 1; H <= r + 1; H++) { //check all around the 0 tile and flip every valid tile that has a basic icon on it
                                            if (H < 0 || H > height - 1) {
                                                continue;
                                            }
                                            for (int W = c - 1; W <= c + 1; W++) {
                                                if (W < 0 || W > width - 1) {
                                                } else {
                                                    if ((butArray[H][W].getIcon()).equals(basic)) {
                                                        butArray[H][W].doClick();
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    case 1:
                                        butArray[r][c].setIcon(one);
                                        break;
                                    case 2:
                                        butArray[r][c].setIcon(two);
                                        break;
                                    case 3:
                                        butArray[r][c].setIcon(three);
                                        break;
                                    case 4:
                                        butArray[r][c].setIcon(four);
                                        break;
                                    case 5:
                                        butArray[r][c].setIcon(five);
                                        break;
                                    case 6:
                                        butArray[r][c].setIcon(six);
                                        break;
                                    case 7:
                                        butArray[r][c].setIcon(seven);
                                        break;
                                    case 8:
                                        butArray[r][c].setIcon(eight);
                                        break;
                                    default:
                                        break;
                                }
                                tilesFlipped += 1; //each time a tile is clicked that is not a mine, tileFlipped increases by one
                                if (tilesFlipped == height * width - totalMines) {
                                    winGame(); //game is won if tiles flipped is the same as all the tiles subtract the number of mines
                                }
                            }
                            else if (numArray[r][c] == -1) { //if the button clicked is a mine
                                butArray[r][c].setIcon(mine); //change icon to mine
                                loseGame(); //call loseGame function
                            }
                        }
                    }
                }
            }
        }            
    }

    private void winGame() {
        gameWon = true; //the game has been won
        endTime = System.currentTimeMillis(); //get the system time at the end
        boolean newHighscore = false; //initialize newHighscore variable as false
        gameTime = (endTime - startTime) / 1000.000; //find the time the game took
        winScreen.setModal(true); //make winScreen modal, must be closed before continuing
        nameInput.setText(""); //clear the nameInput field
        winOutput.setText("Congratulations! You found all the mines in " + gameTime + " seconds!"); //tell how long the game took to complete
        for (int i = 0; i < scoreboard.length/2; i++) {
            if (gameTime <= Double.parseDouble(scoreboard[i*2+1])) { //if the gameTime is less than or equal to one of the top 10 times, then you are added to the board
                newHighscore = true; //set newHighscore to true
                winOutput.append("\nYou also made it to number " + (i+1) + " on the scoreboard, input your name\n" //append extra message that highscore has been found
                        + "below and click submit to be added to the board!\n"
                        + "If you do not want to be added to the scoreboard, click exit to continue.");
                submitButton.setEnabled(true); //allow the submit button to be used
                nameInput.setEnabled(true); //allow the nameInput to be used
                nameInput.requestFocus(); //set focus to nameInput
                break;
            }
        }
        if (newHighscore == false) { //if the time does not qualify for a new highscore, append extra message letting user know
            winOutput.append("\nUnfortunately, you did not find all the mines fast enough to make it on\n"
                    + "the scoreboard. Better luck next time. Click Exit to continue.");
            submitButton.setEnabled(false); //disable submitButton and nameInput field
            nameInput.setEnabled(false);
        }
        winScreen.setVisible(true); //show the winSceen
        flagButton.setEnabled(false); //disable flagButton for when winScreen is closed
        infoOutput.setText("Click on options to start another game or exit to leave the program."); //add message to guide user to next steps
    }

    private void updateScoreboard() {
        try {
            String filepath = "C:\\Minesweeper_v2\\scores.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new FileInputStream(filepath));
            for (int i = 0; i < scoreboard.length/2; i++) { //for each item in the scoreboard array, readd that data back into the xml file and save it
                doc.getElementsByTagName("name").item(i).setTextContent(scoreboard[i*2]);
                doc.getElementsByTagName("time").item(i).setTextContent(scoreboard[i*2+1]);
            }

            //write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result =  new StreamResult(new File(filepath));
            transformer.transform(source, result);

            System.out.println("XML scoreboard updated"); //update is complete

        } catch(ParserConfigurationException | TransformerException | IOException | SAXException pce){
        }
    }
    
    private void loseGame() { //go through all tiles and set their icons
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if ((butArray[r][c].getIcon()).equals(flag) && numArray[r][c] != -1) {
                    butArray[r][c].setIcon(wrongFlag); //if there was a flag on a tile that isn't a mine, set wrongFlag icon
                }
                else if (!(butArray[r][c].getIcon()).equals(flag)) { //if the icon was not a flag, then flip all the tiles to their corresponding values from the numArray
                    switch (numArray[r][c]) {
                        case 0:
                            butArray[r][c].setIcon(zero);
                            break;
                        case 1:
                            butArray[r][c].setIcon(one);
                            break;
                        case 2:
                            butArray[r][c].setIcon(two);
                            break;
                        case 3:
                            butArray[r][c].setIcon(three);
                            break;
                        case 4:
                            butArray[r][c].setIcon(four);
                            break;
                        case 5:
                            butArray[r][c].setIcon(five);
                            break;
                        case 6:
                            butArray[r][c].setIcon(six);
                            break;
                        case 7:
                            butArray[r][c].setIcon(seven);
                            break;
                        case 8:
                            butArray[r][c].setIcon(eight);
                            break;
                        case -1:
                            butArray[r][c].setIcon(mine);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        flagButton.setEnabled(false); //disable flag button so that it is not a distraction
        infoOutput.setText("Nice try. You found " + minesFlagged + "/" + totalMines + " mines. To reset click options, or exit the game."); //show lose game output, options or exit to continue
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        winScreen = new javax.swing.JDialog();
        congratsTitle = new javax.swing.JLabel();
        winScrollArea = new javax.swing.JScrollPane();
        winOutput = new javax.swing.JTextArea();
        nameInputLabel = new javax.swing.JLabel();
        nameInput = new javax.swing.JTextField();
        submitButton = new javax.swing.JButton();
        winExitButton = new javax.swing.JButton();
        optionsScreen = new javax.swing.JDialog();
        continueGameButton = new javax.swing.JButton();
        newGameButton = new javax.swing.JButton();
        scoresButton = new javax.swing.JButton();
        optionsTitle = new javax.swing.JLabel();
        instructButton = new javax.swing.JButton();
        instructLabel = new javax.swing.JLabel();
        newGameLabel = new javax.swing.JLabel();
        newGameLabel1 = new javax.swing.JLabel();
        newGameLabel2 = new javax.swing.JLabel();
        scoreboardScreen = new javax.swing.JDialog();
        scoreTableArea = new javax.swing.JScrollPane();
        scoreTable = new javax.swing.JTable();
        scoreboardTitle = new javax.swing.JLabel();
        scoreboardExitButton = new javax.swing.JButton();
        instructScreen = new javax.swing.JDialog();
        instructionsTitle = new javax.swing.JLabel();
        instructArea = new javax.swing.JScrollPane();
        instructText = new javax.swing.JTextArea();
        instructExitButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        optionsButton = new javax.swing.JButton();
        flagButton = new javax.swing.JToggleButton();
        infoOutput = new javax.swing.JTextField();
        exitButton = new javax.swing.JButton();
        gamePanel = new javax.swing.JPanel();

        winScreen.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        winScreen.setResizable(false);
        winScreen.setSize(new java.awt.Dimension(450, 450));

        congratsTitle.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        congratsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        congratsTitle.setText("Congratulations!");

        winOutput.setEditable(false);
        winOutput.setColumns(20);
        winOutput.setRows(5);
        winScrollArea.setViewportView(winOutput);

        nameInputLabel.setText("Please Insert Your Name Here:");

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        winExitButton.setText("Exit");
        winExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                winExitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout winScreenLayout = new javax.swing.GroupLayout(winScreen.getContentPane());
        winScreen.getContentPane().setLayout(winScreenLayout);
        winScreenLayout.setHorizontalGroup(
            winScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(winScreenLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(winScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, winScreenLayout.createSequentialGroup()
                        .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(winExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(winScreenLayout.createSequentialGroup()
                        .addComponent(nameInputLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nameInput))
                    .addComponent(winScrollArea)
                    .addComponent(congratsTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        winScreenLayout.setVerticalGroup(
            winScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(winScreenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(congratsTitle)
                .addGap(18, 18, 18)
                .addComponent(winScrollArea, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(winScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameInputLabel)
                    .addComponent(nameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(winScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(winExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(63, Short.MAX_VALUE))
        );

        optionsScreen.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        optionsScreen.setBounds(new java.awt.Rectangle(0, 0, 590, 630));
        optionsScreen.setResizable(false);

        continueGameButton.setText("Return to Current Game");
        continueGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continueGameButtonActionPerformed(evt);
            }
        });

        newGameButton.setText("Start New Game");
        newGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameButtonActionPerformed(evt);
            }
        });

        scoresButton.setText("Score Board");
        scoresButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoresButtonActionPerformed(evt);
            }
        });

        optionsTitle.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        optionsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        optionsTitle.setText("Options");

        instructButton.setText("Instructions");
        instructButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instructButtonActionPerformed(evt);
            }
        });

        instructLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        instructLabel.setText("To view the instructions click here:");

        newGameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        newGameLabel.setText("To return to the current game click here:");

        newGameLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        newGameLabel1.setText("To start a new game click here:");

        newGameLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        newGameLabel2.setText("To view the scoreboard click here:");

        javax.swing.GroupLayout optionsScreenLayout = new javax.swing.GroupLayout(optionsScreen.getContentPane());
        optionsScreen.getContentPane().setLayout(optionsScreenLayout);
        optionsScreenLayout.setHorizontalGroup(
            optionsScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsScreenLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(optionsScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newGameLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(newGameLabel2)
                    .addComponent(newGameLabel1)
                    .addComponent(instructLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(optionsScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scoresButton, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(continueGameButton)
                    .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(instructButton, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
            .addComponent(optionsTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        optionsScreenLayout.setVerticalGroup(
            optionsScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsScreenLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(optionsTitle)
                .addGap(65, 65, 65)
                .addGroup(optionsScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(instructButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(instructLabel))
                .addGap(40, 40, 40)
                .addGroup(optionsScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newGameLabel1))
                .addGap(40, 40, 40)
                .addGroup(optionsScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(continueGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newGameLabel))
                .addGap(40, 40, 40)
                .addGroup(optionsScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scoresButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newGameLabel2))
                .addContainerGap(146, Short.MAX_VALUE))
        );

        scoreboardScreen.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        scoreboardScreen.setBounds(new java.awt.Rectangle(0, 0, 700, 700));
        scoreboardScreen.setResizable(false);
        scoreboardScreen.setSize(new java.awt.Dimension(595, 630));

        scoreTableArea.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scoreTableArea.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scoreTableArea.setPreferredSize(new java.awt.Dimension(450, 400));

        scoreTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        scoreTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Placement", "Name", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scoreTable.setGridColor(new java.awt.Color(0, 0, 0));
        scoreTable.setName(""); // NOI18N
        scoreTable.setPreferredSize(new java.awt.Dimension(450, 430));
        scoreTable.setRowHeight(40);
        scoreTable.setSelectionBackground(new java.awt.Color(204, 255, 255));
        scoreTable.getTableHeader().setReorderingAllowed(false);
        scoreTableArea.setViewportView(scoreTable);
        if (scoreTable.getColumnModel().getColumnCount() > 0) {
            scoreTable.getColumnModel().getColumn(0).setResizable(false);
            scoreTable.getColumnModel().getColumn(1).setResizable(false);
            scoreTable.getColumnModel().getColumn(2).setResizable(false);
        }

        scoreboardTitle.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        scoreboardTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scoreboardTitle.setText("High Scores!");

        scoreboardExitButton.setText("Exit");
        scoreboardExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoreboardExitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout scoreboardScreenLayout = new javax.swing.GroupLayout(scoreboardScreen.getContentPane());
        scoreboardScreen.getContentPane().setLayout(scoreboardScreenLayout);
        scoreboardScreenLayout.setHorizontalGroup(
            scoreboardScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scoreboardScreenLayout.createSequentialGroup()
                .addGroup(scoreboardScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scoreboardExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(scoreboardScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(scoreboardScreenLayout.createSequentialGroup()
                            .addGap(100, 100, 100)
                            .addComponent(scoreboardTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(scoreboardScreenLayout.createSequentialGroup()
                            .addGap(75, 75, 75)
                            .addComponent(scoreTableArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        scoreboardScreenLayout.setVerticalGroup(
            scoreboardScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(scoreboardScreenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scoreboardTitle)
                .addGap(18, 18, 18)
                .addComponent(scoreTableArea, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(scoreboardExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        instructScreen.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        instructScreen.setPreferredSize(new java.awt.Dimension(500, 600));
        instructScreen.setResizable(false);
        instructScreen.setSize(new java.awt.Dimension(500, 600));

        instructionsTitle.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        instructionsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        instructionsTitle.setText("Minesweeper Instructions");

        instructText.setEditable(false);
        instructText.setColumns(20);
        instructText.setRows(5);
        instructText.setText("Welcome to Minesweeper!\n\nThis program is a re-creation of the classic game, with a couple slight\nalterations. Please consider reading the rules before exiting to the\ngame.\n\n1. The goal of Minesweeper is to flip all the safe tiles on screen\nby clicking them, while avoiding the ten mines placed randomly\nthroughout the field.\n\n2. When a tile is flipped that is not a mine, the number represents\nthe number of mines surrounding that tile.\n\n3. The flag button in the top left corner is a useful tool for\nmarking the locations of mines you have found. Select the flag button,\nand then click on a tile to place a flag onto it. While a tile is\nflagged it is unflippable. To unflag a tile, make sure the flag button\nis selected and click on the flagged tile once more.\n\n4. If you ever want to start a new game, view the instructions again,\nor see the high score board, click the options button in the top right\nand click whichever option you are looking for.\n\n5. If you want to close the program, simply click the exit button in\nthe bottom right corner of the game screen.\n\nGood luck!");
        instructArea.setViewportView(instructText);

        instructExitButton.setText("Exit");
        instructExitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instructExitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout instructScreenLayout = new javax.swing.GroupLayout(instructScreen.getContentPane());
        instructScreen.getContentPane().setLayout(instructScreenLayout);
        instructScreenLayout.setHorizontalGroup(
            instructScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(instructionsTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
            .addGroup(instructScreenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(instructArea)
                .addContainerGap())
            .addGroup(instructScreenLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(instructExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        instructScreenLayout.setVerticalGroup(
            instructScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(instructScreenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(instructionsTitle)
                .addGap(18, 18, 18)
                .addComponent(instructArea, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(instructExitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 600, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(600, 600));

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        titleLabel.setText("Minesweeper");

        optionsButton.setText("Options");
        optionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsButtonActionPerformed(evt);
            }
        });

        flagButton.setText("Flag");

        infoOutput.setEditable(false);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        gamePanel.setLayout(new java.awt.GridLayout(9, 9));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(flagButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addComponent(titleLabel)
                        .addGap(80, 80, 80)
                        .addComponent(optionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(infoOutput)
                        .addGap(18, 18, 18)
                        .addComponent(exitButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(optionsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(flagButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(infoOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0); //exit button on mainscreen ends the program
    }//GEN-LAST:event_exitButtonActionPerformed

    private void optionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsButtonActionPerformed
        optionsScreen.setModal(true); //options button brings up options screen, which is modal
        optionsScreen.setVisible(true);
    }//GEN-LAST:event_optionsButtonActionPerformed

    private void continueGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continueGameButtonActionPerformed
        optionsScreen.setVisible(false); //continue game closes the options screen, reshowing the current game that was underneath
    }//GEN-LAST:event_continueGameButtonActionPerformed

    private void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameButtonActionPerformed
        //start a new game
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                butArray[r][c].setIcon(basic); //reset all tiles to basic icon 
                numArray[r][c] = 0;//all numArray values to 0
            }
        }
        insertMines(totalMines); //reinsert random mines
        setNeighbourValues(); //set the surrounding tile values for the number of mines they are touching
        reset(); //reset the neccessary variables, buttons and text fields
        optionsScreen.setVisible(false); //hide the options screen again
    }//GEN-LAST:event_newGameButtonActionPerformed

    private void scoresButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoresButtonActionPerformed
        optionsScreen.setVisible(false); //hide the options screen
        scoreboardScreen.setModal(true); //scoreboard is modal
        for (int j = 0; j < 10; j++) { //set the table values
            scoreTable.setValueAt(j+1, j, 0); //ranks 1-10
            scoreTable.setValueAt(scoreboard[j*2], j, 1); //names
            scoreTable.setValueAt(scoreboard[j*2+1], j, 2); //times compelted
        }
        scoreboardScreen.setVisible(true); //show the scoreboard
    }//GEN-LAST:event_scoresButtonActionPerformed

    private void scoreboardExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoreboardExitButtonActionPerformed
        scoreboardScreen.setVisible(false); //hide the scoreboard
        optionsScreen.setVisible(true);//go back to options screen
    }//GEN-LAST:event_scoreboardExitButtonActionPerformed

    private void winExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_winExitButtonActionPerformed
        winScreen.setVisible(false); //close the winScreen if exit button is clicked
    }//GEN-LAST:event_winExitButtonActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        winOutput.setText(""); //clear the winOutput text area
        String name = nameInput.getText(); //get the input for highscore name
        
        //name must only contain letters and cannot be blank
        if (!name.matches("[a-zA-Z]*")) {
            winOutput.append("Your name should only contain letters.\n");
        }
        else if (name.equals("")) {
            winOutput.append("Please do not leave the name input blank.\n");
        }
        else {
            for (int i = 0; i < scoreboard.length/2; i++) { //check through the whole scoreboard, starting from the top
                if (gameTime <= Double.parseDouble(scoreboard[i*2+1])) { //when gameTime is found to be less than the score in place, that's where it belongs
                    for (int j = 9; j > i; j--) { //start from the bottom of the scoreboard, push out the bottom value and move every value down one
                        scoreboard[j*2] = scoreboard[(j-1)*2];
                        scoreboard[j*2+1] = scoreboard[(j-1)*2+1];
                    }
                    scoreboard[i*2] = name; //add the name to the scoreboard array
                    scoreboard[i*2+1] = Double.toString(gameTime); //add the time to the scoreboard array
                    updateScoreboard(); //update the scoreboard to include the new data
                    winScreen.setVisible(false); //hide the winscreen after submission
                    break;
                }
            }
        }
    }//GEN-LAST:event_submitButtonActionPerformed

    private void instructExitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instructExitButtonActionPerformed
        instructScreen.setVisible(false); //exit button from instruct hides itself
    }//GEN-LAST:event_instructExitButtonActionPerformed

    private void instructButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instructButtonActionPerformed
        instructScreen.setModal(true); //instruction screen is modal
        instructScreen.setVisible(true); //show the instructions when instructions button is clicked
    }//GEN-LAST:event_instructButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreenUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainScreenUI().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel congratsTitle;
    private javax.swing.JButton continueGameButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JToggleButton flagButton;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JTextField infoOutput;
    private javax.swing.JScrollPane instructArea;
    private javax.swing.JButton instructButton;
    private javax.swing.JButton instructExitButton;
    private javax.swing.JLabel instructLabel;
    private javax.swing.JDialog instructScreen;
    private javax.swing.JTextArea instructText;
    private javax.swing.JLabel instructionsTitle;
    private javax.swing.JTextField nameInput;
    private javax.swing.JLabel nameInputLabel;
    private javax.swing.JButton newGameButton;
    private javax.swing.JLabel newGameLabel;
    private javax.swing.JLabel newGameLabel1;
    private javax.swing.JLabel newGameLabel2;
    private javax.swing.JButton optionsButton;
    private javax.swing.JDialog optionsScreen;
    private javax.swing.JLabel optionsTitle;
    private javax.swing.JTable scoreTable;
    private javax.swing.JScrollPane scoreTableArea;
    private javax.swing.JButton scoreboardExitButton;
    private javax.swing.JDialog scoreboardScreen;
    private javax.swing.JLabel scoreboardTitle;
    private javax.swing.JButton scoresButton;
    private javax.swing.JButton submitButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton winExitButton;
    private javax.swing.JTextArea winOutput;
    private javax.swing.JDialog winScreen;
    private javax.swing.JScrollPane winScrollArea;
    // End of variables declaration//GEN-END:variables
}
