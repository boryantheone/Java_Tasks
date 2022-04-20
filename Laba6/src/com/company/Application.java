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
        frame.setTitle("СontrolWindow");
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

        @Override // Метод от Observable
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

        public void actionPerformed(ActionEvent aE) { // обработка нажатий 2-х кнопок
            String str = aE.getActionCommand();
            if (str.equals("startItem")) {// обработка нажатия на кнопку "Пуск"
                if (count == maxCount) {
                    JOptionPane.showMessageDialog(frame, "You cannot add more than 10 items");
                    return;
                }
                buttonOKIsPressed();
            }
            else if (str.equals("changeNumber")) {// обработка нажатия на кнопку "Изменить"
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
                figure.addObserver(this); // добавялем объект к слушателям и он тоже будет обновляться
            }
        }

        // использовали Builder чтобы не писать длинный конструктор из 5 аргументов
        private Figure getFigureByFigureBuilder(ObjectType objectType, int number) throws NumberFormatException {
            return Figure.FigureBuilder.aFigure()
                    .withColor(color)
                    .withNumber(number)
                    .withObjectType(objectType)
                    .withText(this.text.getText())
                    .withSpeedX( Integer.parseInt(xVelocityText.getText())) // здесь может быть исключение,
                    .withSpeedY(Integer.parseInt(yVelocityText.getText())) // но это не беда, тк мы можем просто исключить эти методы
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

        // Создали конструктор который принимает наш Builder, он позволит нам без проблем получить все атрибуты
        public Figure(FigureBuilder builder){
            color = builder.color;
            text = builder.text;
            speedX = builder.speedX;
            speedY = builder.speedY;
            objectType = builder.objectType;
            number = builder.number;
            count++;
            thr = new Thread(this, count + ":" + text + ":");
            thr.start(); // врубаем новый поток для фигуры и переходим в run
        }

        public Figure(Color color, String text, ObjectType type) {
            objectType = type;
            this.text = text;
            this.color = color;
            count++;
            thr = new Thread(this, count + ":" + text + ":");
            thr.start(); // запускаем новый поток для элемента и переходим в run
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

        //  использую паттерн проектирования Builder
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

            // собираем все наши полученные атрибуты и передаем в
            // нужный класс через передачу нашего Builder
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
