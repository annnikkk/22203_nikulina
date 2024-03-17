#include "game_qt.h"
#include <QCoreApplication>
#include <QFile>
#include <QPixmap>
#include <QPainter>
#include <QImage>
#include <QtCore/QTimer>

game_qt::game_qt(int level) {
    cur_level = level;
    game_time = 0;
    timer = new QTimer(this);
    timer->setInterval(100);
    connect(timer, &QTimer::timeout, this, &game_qt::onTimeout);
    timer->start();
    timer_label = new QLabel(this);
    timer_label->setGeometry(100, 10, 150, 30);
    timer_label->setText("Time: 0");
    socoban_game.start_game(cur_level);
    restart_button = new QPushButton("Restart", this);
    restart_button->setGeometry(10, 10, 80, 30);
    restart_button->setFocusPolicy(Qt::NoFocus);
    connect(restart_button, SIGNAL(clicked()), this, SLOT(restartGame()));
    setMinimumSize(500, 500);

}

void game_qt::paintEvent(QPaintEvent *)
{
    QPainter painter(this);
    painter.setRenderHint(QPainter::Antialiasing, true);
    painter.setRenderHint(QPainter::SmoothPixmapTransform, true);

    drawing_field(&painter);
}

void game_qt::keyPressEvent(QKeyEvent *event){
    switch (event->key()) {
    case Qt::Key_Up:
        socoban_game.movement('u');
        break;
    case Qt::Key_Down:
        socoban_game.movement('d');
        break;
    case Qt::Key_Left:
        socoban_game.movement('l');
        break;
    case Qt::Key_Right:
        socoban_game.movement('r');
        break;
    default:
        return;
    }
    update();
}

void game_qt::onTimeout(){
    game_time += 0.1;
    QFont font = timer_label->font();
    font.setWeight(QFont::Bold);
    font.setFamily("Arial");
    font.setPointSize(15);
    timer_label->setFont(font);
    timer_label->setText(QString("Time: %1").arg(game_time, 0, 'f', 1));

    timer_label->setAutoFillBackground(true);
    QPalette palette = timer_label->palette();
    palette.setColor(QPalette::Window, Qt::white);
    timer_label->setPalette(palette);}

void game_qt::restartGame()
{
    game_time = 0;
    socoban_game.start_game(cur_level);
    update();
}

void game_qt::resizeEvent(QResizeEvent *event) {
    int smallerDimension = std::min(width(), height());
    socoban_game.get_field().set_field_X((width() - smallerDimension) / 2);
    socoban_game.get_field().set_field_Y((height() - smallerDimension) / 2);
}



void game_qt::drawing_field(QPainter* painter){

    if(socoban_game.win_condition == true){
        timer->stop();
        if(socoban_game.check_table_records(cur_level, game_time, player_name)){
            QMessageBox::information(this, "Win", "You win! You're on the leaderboard!");
            this->close();
        }
        else{
            QMessageBox::information(this, "Win", "You win!");
            this->close();
        }
    }

    painter->setBrush(field_color);

    painter->drawRect(socoban_game.get_field().get_field_X(), socoban_game.get_field().get_field_Y(), socoban_game.get_field().get_field_size(), socoban_game.get_field().get_field_size());

    int fieldSize = std::min(width(), height());
    int smallSquareSize = fieldSize / 10;

    if (fieldSize % 10 != 0) {
        smallSquareSize = fieldSize / 10 - 1;
    }

    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10; j++) {
            const int index = i * 10 + j;
            const int small_squareX = static_cast<int>(socoban_game.get_field().get_field_X() + j * smallSquareSize);
            const int small_squareY = static_cast<int>(socoban_game.get_field().get_field_Y() + i * smallSquareSize);
            switch (socoban_game.get_field().get_type(j,i)) {
            case field::tile_types::wall:
                painter->setBrush(QColor("#9b2226"));
                painter->drawRect(small_squareX, small_squareY, smallSquareSize, smallSquareSize);
                continue;
            case field::tile_types::box_place:
                painter->setBrush(QColor("#fb8b24"));
                painter->drawRect(small_squareX, small_squareY, smallSquareSize, smallSquareSize);
                continue;
            case field::tile_types::box:
                painter->setBrush(QColor("#9c6644"));
                painter->drawRect(small_squareX, small_squareY, smallSquareSize, smallSquareSize);
                continue;
            case field::tile_types::player:
                socoban_game.get_player().set_position(i, j, 10);
                painter->setBrush(QColor("#9d4edd"));
                painter->drawRect(small_squareX, small_squareY, smallSquareSize, smallSquareSize);
                continue;
            case field::tile_types::box_on_place:
                painter->setBrush(QColor("#00ad44"));
                painter->drawRect(small_squareX, small_squareY, smallSquareSize, smallSquareSize);
                continue;
            case field::tile_types::player_on_box_place:
                socoban_game.get_player().set_position(i, j, 10);
                painter->setBrush(QColor("#9d4edd"));
                painter->drawRect(small_squareX, small_squareY, smallSquareSize, smallSquareSize);
                continue;
            default:
                painter->setBrush(field_color);
                painter->drawRect(small_squareX, small_squareY, smallSquareSize, smallSquareSize);
                continue;
            }
        }
    }
}

void game_qt::set_name(std::string name){
    player_name = name;
}
