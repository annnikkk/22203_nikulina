#include "mainwindow.h"

levelSelectionWindow::levelSelectionWindow(QWidget *parent) : QWidget(parent) {
    setFixedSize(600, 600);
    QPalette palette = this->palette();
    palette.setColor(QPalette::Window, QColorConstants::Svg::mediumpurple);
    setPalette(palette);

    level1Button = new QPushButton("Level 1", this);
    level2Button = new QPushButton("Level 2", this);
    level3Button = new QPushButton("Level 3", this);
    level4Button = new QPushButton("Level 4", this);
    level5Button = new QPushButton("Level 5", this);

    connect(level1Button, &QPushButton::clicked, this, &levelSelectionWindow::onLevel1ButtonClicked);
    connect(level2Button, &QPushButton::clicked, this, &levelSelectionWindow::onLevel2ButtonClicked);
    connect(level3Button, &QPushButton::clicked, this, &levelSelectionWindow::onLevel3ButtonClicked);
    connect(level4Button, &QPushButton::clicked, this, &levelSelectionWindow::onLevel4ButtonClicked);
    connect(level5Button, &QPushButton::clicked, this, &levelSelectionWindow::onLevel5ButtonClicked);


    layout = new QVBoxLayout(this);
    layout->addWidget(level1Button);
    layout->addWidget(level2Button);
    layout->addWidget(level3Button);
    layout->addWidget(level4Button);
    layout->addWidget(level5Button);

    setLayout(layout);
}

void levelSelectionWindow::onLevel1ButtonClicked() {
    bool ok;
    QString playerName = QInputDialog::getText(this, "Enter Your Name", "Player Name:", QLineEdit::Normal, "", &ok);
    if (ok && !playerName.isEmpty()) {
        game_qt *gameWindow = new game_qt(1);
        gameWindow->set_name(playerName.toStdString());
        gameWindow->show();
        this->close();
    }
}

void levelSelectionWindow::onLevel2ButtonClicked() {
    bool ok;
    QString playerName = QInputDialog::getText(this, "Enter Your Name", "Player Name:", QLineEdit::Normal, "", &ok);
    if (ok && !playerName.isEmpty()) {
        game_qt *gameWindow = new game_qt(2);
        gameWindow->set_name(playerName.toStdString());
        gameWindow->show();
        this->close();
    }
}

void levelSelectionWindow::onLevel3ButtonClicked() {
    bool ok;
    QString playerName = QInputDialog::getText(this, "Enter Your Name", "Player Name:", QLineEdit::Normal, "", &ok);
    if (ok && !playerName.isEmpty()) {
        game_qt *gameWindow = new game_qt(3);
        gameWindow->set_name(playerName.toStdString());
        gameWindow->show();
        this->close();
    }
}

void levelSelectionWindow::onLevel4ButtonClicked() {
    bool ok;
    QString playerName = QInputDialog::getText(this, "Enter Your Name", "Player Name:", QLineEdit::Normal, "", &ok);
    if (ok && !playerName.isEmpty()) {
        game_qt *gameWindow = new game_qt(4);
        gameWindow->set_name(playerName.toStdString());
        gameWindow->show();
        this->close();
    }
}

void levelSelectionWindow::onLevel5ButtonClicked() {
    bool ok;
    QString playerName = QInputDialog::getText(this, "Enter Your Name", "Player Name:", QLineEdit::Normal, "", &ok);
    if (ok && !playerName.isEmpty()) {
        game_qt *gameWindow = new game_qt(5);
        gameWindow->set_name(playerName.toStdString());
        gameWindow->show();
        this->close();
    }
}

std::vector<std::pair<std::string, double>> get_score_table(std::ifstream& fin){
    std::vector<std::pair<std::string, double>> table;
    std::string name;
    double time;
    while(fin >> name >> time){
        table.push_back({name, time});
    }
    return table;
}

ScoreTableWindow::ScoreTableWindow(const std::string& path) {
    std::ifstream fin(path);
    std::vector<std::pair<std::string, double>> scoreTable = get_score_table(fin);
    int numRows = scoreTable.size();
    int numCols = 2;

    layout = new QVBoxLayout(this);
    tableWidget = new QTableWidget(numRows, numCols, this);
    tableWidget->setHorizontalHeaderLabels(QStringList() << "Name" << "Time");

    for (int row = 0; row < numRows; row++) {
        QTableWidgetItem *nameItem = new QTableWidgetItem(QString::fromStdString(scoreTable[row].first));
        tableWidget->setItem(row, 0, nameItem);

        QTableWidgetItem *timeItem = new QTableWidgetItem(QString::number(scoreTable[row].second));
        tableWidget->setItem(row, 1, timeItem);
    }

    tableWidget->horizontalHeader()->setSectionResizeMode(QHeaderView::Stretch);
    tableWidget->verticalHeader()->setSectionResizeMode(QHeaderView::Stretch);

    QFont font;
    font.setPointSize(18);
    tableWidget->setFont(font);

    layout->addWidget(tableWidget);
    setFixedSize(600, 600);
}

leaderboardWindow::leaderboardWindow(QWidget *parent) : QWidget(parent) {
    setFixedSize(600, 600);
    QPalette palette = this->palette();
    palette.setColor(QPalette::Window, QColorConstants::Svg::mediumpurple);
    setPalette(palette);

    QPushButton *level_1Button = new QPushButton("Level 1", this);
    QPushButton *level_2Button = new QPushButton("Level 2", this);
    QPushButton *level_3Button = new QPushButton("Level 3", this);
    QPushButton *level_4Button = new QPushButton("Level 4", this);
    QPushButton *level_5Button = new QPushButton("Level 5", this);


    connect(level_1Button, &QPushButton::clicked, this, &leaderboardWindow::onLevel_1ButtonClicked);
    connect(level_2Button, &QPushButton::clicked, this, &leaderboardWindow::onLevel_2ButtonClicked);
    connect(level_3Button, &QPushButton::clicked, this, &leaderboardWindow::onLevel_3ButtonClicked);
    connect(level_4Button, &QPushButton::clicked, this, &leaderboardWindow::onLevel_4ButtonClicked);
    connect(level_5Button, &QPushButton::clicked, this, &leaderboardWindow::onLevel_5ButtonClicked);


    QVBoxLayout *layout = new QVBoxLayout(this);
    layout->addWidget(level_1Button);
    layout->addWidget(level_2Button);
    layout->addWidget(level_3Button);
    layout->addWidget(level_4Button);
    layout->addWidget(level_5Button);

    setLayout(layout);
}

void leaderboardWindow::onLevel_1ButtonClicked() {
    ScoreTableWindow* ScoreTable = new ScoreTableWindow("../socoban/level1_table.txt");
    ScoreTable->show();
}

void leaderboardWindow::onLevel_2ButtonClicked() {
    ScoreTableWindow* ScoreTable = new ScoreTableWindow("../socoban/level2_table.txt");
    ScoreTable->show();
}

void leaderboardWindow::onLevel_3ButtonClicked() {
    ScoreTableWindow* ScoreTable = new ScoreTableWindow("../socoban/level3_table.txt");
    ScoreTable->show();
}

void leaderboardWindow::onLevel_4ButtonClicked() {
    ScoreTableWindow* ScoreTable = new ScoreTableWindow("../socoban/level4_table.txt");
    ScoreTable->show();
}

void leaderboardWindow::onLevel_5ButtonClicked() {
    ScoreTableWindow* ScoreTable = new ScoreTableWindow("../socoban/level5_table.txt");
    ScoreTable->show();
}

mainWindow::mainWindow(QWidget *parent) : QWidget(parent) {
    setFixedSize(600, 600);
    QPalette palette = this->palette();
    palette.setColor(QPalette::Window, QColorConstants::Svg::mediumpurple);
    setPalette(palette);

    playButton = new QPushButton("PLAY", this);
    leaderboardButton = new QPushButton("LEADERBOARD", this);


    QPalette buttonPalette = leaderboardButton->palette();
    buttonPalette.setColor(QPalette::Button, QColorConstants::Svg::sandybrown);
    leaderboardButton->setAutoFillBackground(true);
    leaderboardButton->setPalette(buttonPalette);
    leaderboardButton->update();

    QFont buttonFont = leaderboardButton->font();
    buttonFont.setFamily("Arial");
    buttonFont.setPointSize(16);
    buttonFont.setBold(true);
    leaderboardButton->setFont(buttonFont);

    QPalette buttonPalette2 = playButton->palette();
    buttonPalette2.setColor(QPalette::Button, QColorConstants::Svg::sandybrown);
    playButton->setAutoFillBackground(true);
    playButton->setPalette(buttonPalette2);
    playButton->update();

    QFont buttonFont2 = playButton->font();
    buttonFont2.setFamily("Arial");
    buttonFont2.setPointSize(16);
    buttonFont2.setBold(true);
    playButton->setFont(buttonFont2);

    connect(playButton, &QPushButton::clicked, this, &mainWindow::onPlayButtonClicked);
    connect(leaderboardButton, &QPushButton::clicked, this, &mainWindow::onleaderboardButtonClicked);

    QVBoxLayout *layout = new QVBoxLayout(this);
    layout->addWidget(playButton);
    layout->addWidget(leaderboardButton);
    setLayout(layout);
}

void mainWindow::onleaderboardButtonClicked(int level) {
    leaderboardWindow *leaderboardWindow_ = new leaderboardWindow();
    leaderboardWindow_->show();
    this->close();
}
void mainWindow::onPlayButtonClicked(){
    levelSelectionWindow *levelWindow = new levelSelectionWindow();
    levelWindow->show();
    this->close();
}
