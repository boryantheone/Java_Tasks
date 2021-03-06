package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ColorChooserUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Application {
    static int count;
    static int maxCount = 10;
    static MainFrame mainFrame;

    public static void main(String[] args) throws IOException {
        count = 0;
        mainFrame = new MainFrame();
    }

    static class MainFrame extends Frame implements Observer, ActionListener, ItemListener {
        private final int width = 500;
        private final int height = 500;


        private final LinkedList<Figure> figuresList = new LinkedList<Figure>();
        private Color       color;
        private Frame       frame;
        private Button      startItemButton;
        private Button      changeItemButton;
        private JLabel      alertItemCountLabel;
        private Choice      choiceObjectType;
        private Choice      xVelocityChoice;
        private Choice      yVelocityChoice;
        private TextField   itemNumberText;
        private TextField   text;
        private TextField   xVelocityText;
        private TextField   yVelocityText;
        private TextField   choiceAlreadyAddedItemText;
        private TextField   newItemNumber;
        JColorChooser       choiceColorsTable;

        BufferedImage image;

        MainFrame() throws IOException {

        this.addWindowListener(new WindowAdapter2());

        //main frame
        frame = new JFrame();
        frame.setSize(new Dimension(800, 600));
        frame.setTitle("–°ontrolWindow");
//        frame.setLayout(new FlowLayout());
        frame.setLayout(new FlowLayout(FlowLayout.CENTER) );
        frame.addWindowListener(new WindowAdapter2());

        //choice color
        choiceColorsTable = new JColorChooser();
        frame.add(new Label("Choose color of item: "));
        frame.add(choiceColorsTable);

        //choice type
        choiceObjectType = new Choice();
        choiceObjectType.addItem(ObjectType.IMAGE.getType());
        choiceObjectType.addItem(ObjectType.TEXT.getType());
        frame.add(new Label("Choose type of item: "));
        frame.add(choiceObjectType);

        //enter text for item
        frame.add(new Label("Enter the text for item: "));
        text = new TextField(5);
        frame.add(text);

        //enter x/y velocity
        frame.add(new Label("Enter a velocity value for x: "));
        xVelocityText = new TextField(5);
        frame.add(xVelocityText);

        frame.add(new Label("Enter a velocity value for y: "));
        yVelocityText = new TextField(5);
        frame.add(yVelocityText);

        //button start
        startItemButton = new Button("Start");
        startItemButton.setSize(new Dimension(10, 40));
        startItemButton.setActionCommand("startItem");
        startItemButton.addActionListener(this);
//        startItemButton.setEnabled(false);
        frame.add(startItemButton);

        //choose number of item
        frame.add(new Label("Enter the number of the running item: "));
        choiceAlreadyAddedItemText = new TextField(5);
        frame.add(choiceAlreadyAddedItemText);

        //change number of item
        frame.add(new Label("Enter the NEW number of item"));
        newItemNumber = new TextField(5);
        frame.add(newItemNumber);

        //choice velocity
        frame.add(new Label("Choose the NEW velocity for x and y"));
        xVelocityChoice = new Choice();
        xVelocityChoice.addItem("1");
        xVelocityChoice.addItem("2");
        xVelocityChoice.addItem("4");
        xVelocityChoice.addItem("6");
        xVelocityChoice.addItem("8");
        xVelocityChoice.addItemListener(this);
        frame.add(xVelocityChoice);

        yVelocityChoice = new Choice();
        yVelocityChoice.addItem("1");
        yVelocityChoice.addItem("2");
        yVelocityChoice.addItem("4");
        yVelocityChoice.addItem("6");
        yVelocityChoice.addItem("8");
        yVelocityChoice.addItemListener(this);
        frame.add(yVelocityChoice);

        //button change
        changeItemButton = new Button("Change");
        changeItemButton.setSize(new Dimension(10, 40));
        changeItemButton.setActionCommand("changeNumber");
        changeItemButton.addActionListener(this);
//        changeItemButton.setEnabled(false);
        frame.add(changeItemButton);


        frame.setVisible(true);
        this.setSize(width, height);
        this.setVisible(true);
        this.setLocation(150, 200);
        this.setTitle("displayWindow");

        File file = new File("/Users/jcollin/Desktop/Java_Labs/Laba6/src/com/company/jaba.png");
        image = ImageIO.read(file);
        }

        @Override // –ú–Ķ—ā–ĺ–ī –ĺ—ā Observable
        public void update(Observable o, Object arg) {
            Figure figure = (Figure) arg;
            System.out.println("x= " + figure.thr.getName() + figure.x);
            repaint();
        }

        public void paint(Graphics g) {
            if (!figuresList.isEmpty()) {
                for (Figure figure : figuresList) {
                    g.setColor(figure.color);
                    g.drawString(Integer.toString(figure.number), figure.x, figure.y);
                    if (figure.objectType == ObjectType.IMAGE) {
                        //g.drawOval(figure.x, figure.y, 50, 50);
                        g.drawImage(image, figure.x, figure.y, 50, 50, null);
                    }
                    else {
                        Font font = new Font("Serif", Font.BOLD, 25);
                        g.setFont(font);
                        g.drawString(figure.getText(), figure.x, figure.y + 25);
                        g.setFont(new Font("Dialog", Font.ITALIC, 15));
                    }
                }
            }
        }

        public void actionPerformed(ActionEvent aE) { // –ĺ–Ī—Ä–į–Ī–ĺ—ā–ļ–į –Ĺ–į–∂–į—ā–ł–Ļ 2-—Ö –ļ–Ĺ–ĺ–Ņ–ĺ–ļ
            String str = aE.getActionCommand();
            if (str.equals("startItem")) {// –ĺ–Ī—Ä–į–Ī–ĺ—ā–ļ–į –Ĺ–į–∂–į—ā–ł—Ź –Ĺ–į –ļ–Ĺ–ĺ–Ņ–ļ—É "–ü—É—Ā–ļ"
                if (count == maxCount) {
                    JOptionPane.showMessageDialog(frame, "You cannot add more than 10 items");
                    return;
                }
                buttonOKIsPressed();
            }
            else if (str.equals("changeNumber")) {// –ĺ–Ī—Ä–į–Ī–ĺ—ā–ļ–į –Ĺ–į–∂–į—ā–ł—Ź –Ĺ–į –ļ–Ĺ–ĺ–Ņ–ļ—É "–ė–∑–ľ–Ķ–Ĺ–ł—ā—Ć"
                try {
                    if (figuresList.isEmpty())
                        return;
                    int changeableNumber = Integer.parseInt(choiceAlreadyAddedItemText.getText());
                    for (int i = 0; i < figuresList.size(); i++) {
                        if (figuresList.get(i).number == changeableNumber){
                            color = getColorFromTable();
                            try {
                                changeFigureWithNewNumberByIndex(i);
                            }
                            catch (Exception ignored) {
                                changeFigureByIndex(i);
                            }
                        }
                    }
                }
                catch (Exception ignored) {
                    return;
                }
            }
            repaint();
        }

        private void changeFigureByIndex(int figureIndex) {
            Figure figure = figuresList.get(figureIndex);
            figure.setSpeedX(getChoiceSpeedXFromTable());
            figure.setSpeedY(getChoiceSpeedYFromTable());
            figure.setColor(color);
        }

        private void changeFigureWithNewNumberByIndex(int figureIndex) {
            Figure figure = figuresList.get(figureIndex);
            int newNumber = Integer.parseInt(newItemNumber.getText());
            figure.number = getUniqueNumberForFigure(newNumber);
            figure.setSpeedX(getChoiceSpeedXFromTable());
            figure.setSpeedY(getChoiceSpeedYFromTable());
            figure.setColor(color);
        }

        private int getUniqueNumberForFigure(int newFigureNumber) {
            if (newFigureNumber == 0)
                newFigureNumber++;
            for (int i = 0; i < figuresList.size(); i++){
                if (figuresList.get(i).number == newFigureNumber){
                    newFigureNumber++;
                    i = -1;
                }
            }
            return newFigureNumber;
        }

        private int getChoiceSpeedXFromTable() {
            switch (xVelocityChoice.getSelectedIndex()) {
                case 0:
                    return 1;
                case 1:
                    return 2;
                case 2:
                    return 4;
                case 3:
                    return 6;
                case 4:
                    return 8;
            }
            return 1;
        }

        private int getChoiceSpeedYFromTable() {
            switch (yVelocityChoice.getSelectedIndex()) {
                case 0:
                    return 1;
                case 1:
                    return 2;
                case 2:
                    return 4;
                case 3:
                    return 6;
                case 4:
                    return 8;
            }
            return 1;
        }

        private void buttonOKIsPressed() {
            Figure figure = null;
            int newFigureNumber = 0;
            color = getColorFromTable();
            ObjectType objectType = getObjectType();
            try {
                newFigureNumber = getUniqueNumberForFigure(newFigureNumber);
                figure = getFigureByFigureBuilder(objectType, newFigureNumber);
            }
            catch (Exception ignored) {
            }
            finally {
                if (newFigureNumber == 0){
                    newFigureNumber = getUniqueNumberForFigure(newFigureNumber);
                }
                if (figure == null){
                    figure = getFigureByFigureBuilderWithoutSpeeds(objectType, newFigureNumber);
                }
                figuresList.add(figure);
                figure.addObserver(this); // –ī–ĺ–Ī–į–≤—Ź–Ľ–Ķ–ľ –ĺ–Ī—ä–Ķ–ļ—ā –ļ —Ā–Ľ—É—ą–į—ā–Ķ–Ľ—Ź–ľ –ł –ĺ–Ĺ —ā–ĺ–∂–Ķ –Ī—É–ī–Ķ—ā –ĺ–Ī–Ĺ–ĺ–≤–Ľ—Ź—ā—Ć—Ā—Ź
            }
        }

        // –ł—Ā–Ņ–ĺ–Ľ—Ć–∑–ĺ–≤–į–Ľ–ł Builder —á—ā–ĺ–Ī—č –Ĺ–Ķ –Ņ–ł—Ā–į—ā—Ć –ī–Ľ–ł–Ĺ–Ĺ—č–Ļ –ļ–ĺ–Ĺ—Ā—ā—Ä—É–ļ—ā–ĺ—Ä –ł–∑ 5 –į—Ä–≥—É–ľ–Ķ–Ĺ—ā–ĺ–≤
        private Figure getFigureByFigureBuilder(ObjectType objectType, int number) throws NumberFormatException {
            return Figure.FigureBuilder.aFigure()
                    .withColor(color)
                    .withNumber(number)
                    .withObjectType(objectType)
                    .withText(this.text.getText())
                    .withSpeedX( Integer.parseInt(xVelocityText.getText())) // –∑–ī–Ķ—Ā—Ć –ľ–ĺ–∂–Ķ—ā –Ī—č—ā—Ć –ł—Ā–ļ–Ľ—é—á–Ķ–Ĺ–ł–Ķ,
                    .withSpeedY(Integer.parseInt(yVelocityText.getText())) // –Ĺ–ĺ —ć—ā–ĺ –Ĺ–Ķ –Ī–Ķ–ī–į, —ā–ļ –ľ—č –ľ–ĺ–∂–Ķ–ľ –Ņ—Ä–ĺ—Ā—ā–ĺ –ł—Ā–ļ–Ľ—é—á–ł—ā—Ć —ć—ā–ł –ľ–Ķ—ā–ĺ–ī—č
                    .build();
        }

        private Figure getFigureByFigureBuilderWithoutSpeeds(ObjectType objectType, int number) {
            return Figure.FigureBuilder.aFigure()
                    .withColor(color)
                    .withNumber(number)
                    .withObjectType(objectType)
                    .withText(this.text.getText())
                    .build();
        }

        private ObjectType getObjectType() {
            switch (choiceObjectType.getSelectedIndex()) {
                case 0:
                    return ObjectType.IMAGE;
                case 1:
                    return ObjectType.TEXT;
            }
            return ObjectType.TEXT;
        }

        private Color getColorFromTable() {
            return choiceColorsTable.getColor();
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
        }

    }

    static class Figure extends Observable implements Runnable {
        Thread thr;
        private boolean xplus = true;
        private boolean yplus = true;
        public ObjectType objectType;
        private int speedX = 1;
        private int speedY = 1;

        final static int WIDTH = 50;
        final static int HEIGHT = 50;
        int x = 0;
        int y = 30;
        int number;
        private final String text;
        private Color color;

        // –°–ĺ–∑–ī–į–Ľ–ł –ļ–ĺ–Ĺ—Ā—ā—Ä—É–ļ—ā–ĺ—Ä –ļ–ĺ—ā–ĺ—Ä—č–Ļ –Ņ—Ä–ł–Ĺ–ł–ľ–į–Ķ—ā –Ĺ–į—ą Builder, –ĺ–Ĺ –Ņ–ĺ–∑–≤–ĺ–Ľ–ł—ā –Ĺ–į–ľ –Ī–Ķ–∑ –Ņ—Ä–ĺ–Ī–Ľ–Ķ–ľ –Ņ–ĺ–Ľ—É—á–ł—ā—Ć –≤—Ā–Ķ –į—ā—Ä–ł–Ī—É—ā—č
        public Figure(FigureBuilder builder){
            color = builder.color;
            text = builder.text;
            speedX = builder.speedX;
            speedY = builder.speedY;
            objectType = builder.objectType;
            number = builder.number;
            count++;
            thr = new Thread(this, count + ":" + text + ":");
            thr.start(); // –≤—Ä—É–Ī–į–Ķ–ľ –Ĺ–ĺ–≤—č–Ļ –Ņ–ĺ—ā–ĺ–ļ –ī–Ľ—Ź —Ą–ł–≥—É—Ä—č –ł –Ņ–Ķ—Ä–Ķ—Ö–ĺ–ī–ł–ľ –≤ run
        }

        public Figure(Color color, String text, ObjectType type) {
            objectType = type;
            this.text = text;
            this.color = color;
            count++;
            thr = new Thread(this, count + ":" + text + ":");
            thr.start(); // –∑–į–Ņ—É—Ā–ļ–į–Ķ–ľ –Ĺ–ĺ–≤—č–Ļ –Ņ–ĺ—ā–ĺ–ļ –ī–Ľ—Ź —ć–Ľ–Ķ–ľ–Ķ–Ĺ—ā–į –ł –Ņ–Ķ—Ä–Ķ—Ö–ĺ–ī–ł–ľ –≤ run
        }

        public int getSpeedX() {
            return speedX;
        }

        public void setSpeedX(int speedX) {
            this.speedX = speedX;
        }

        public int getSpeedY() {
            return speedY;
        }

        public void setColor(Color color) {
            this.color = color;
        }

        public void setSpeedY(int speedY) {
            this.speedY = speedY;
        }

        public String getText() {
            return text;
        }

        public void run() {
            while (true) {
                Dimension mainFrameSize = mainFrame.getSize();
                if (x > mainFrameSize.width - WIDTH)
                    xplus = false;
                if (x < -1)
                    xplus = true;
                if (y > mainFrameSize.height - HEIGHT)
                    yplus = false;
                if (y < 31)
                    yplus = true;
                if (xplus)
                    x += speedX;
                else
                    x -= speedX;
                if (yplus)
                    y += speedY;
                else
                    y -= speedY;
                setChanged();
                notifyObservers(this);
                try {
                    Thread.sleep(10);
                }
                catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        //  –ł—Ā–Ņ–ĺ–Ľ—Ć–∑—É—é –Ņ–į—ā—ā–Ķ—Ä–Ĺ –Ņ—Ä–ĺ–Ķ–ļ—ā–ł—Ä–ĺ–≤–į–Ĺ–ł—Ź Builder
        public static class FigureBuilder {
            private ObjectType  objectType = ObjectType.IMAGE;
            private int         speedX = 1;
            private int         speedY = 1;
            private String      text = "default text";
            private Color       color = Color.black;
            private int number;

            private FigureBuilder(){
            }

            public static FigureBuilder aFigure() {
                return new FigureBuilder();
            }

            public FigureBuilder withColor(Color color){
                this.color = color;
                return this;
            }

            public Figure.FigureBuilder withNumber(int number){
                this.number = number;
                return this;
            }

            public FigureBuilder withText(String text) {
                this.text = text;
                return this;
            }

            public FigureBuilder withSpeedX(int speedX) {
                this.speedX = speedX;
                return this;
            }

            public FigureBuilder withSpeedY(int speedY) {
                this.speedY = speedY;
                return this;
            }

            public FigureBuilder withObjectType(ObjectType type) {
                this.objectType = type;
                return this;
            }

            // —Ā–ĺ–Ī–ł—Ä–į–Ķ–ľ –≤—Ā–Ķ –Ĺ–į—ą–ł –Ņ–ĺ–Ľ—É—á–Ķ–Ĺ–Ĺ—č–Ķ –į—ā—Ä–ł–Ī—É—ā—č –ł –Ņ–Ķ—Ä–Ķ–ī–į–Ķ–ľ –≤
            // –Ĺ—É–∂–Ĺ—č–Ļ –ļ–Ľ–į—Ā—Ā —á–Ķ—Ä–Ķ–∑ –Ņ–Ķ—Ä–Ķ–ī–į—á—É –Ĺ–į—ą–Ķ–≥–ĺ Builder
            public Figure build() {
                return new Figure(this);
            }
        }
    }

    static class WindowAdapter2 extends WindowAdapter {
        @Override
        public void windowClosing (WindowEvent wE) {
            System.exit (0);
        }
    }

    static enum ObjectType {
        IMAGE("Picture"),
        TEXT("Text");

        private final String type;

        ObjectType(String type){
            this.type = type;
        }

        private String getType(){
            return type;
        }
    }
}
