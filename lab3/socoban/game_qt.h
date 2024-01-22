#ifndef GAME_QT_H
#define GAME_QT_H

#include "game.h"

#include <QApplication>
#include <QWidget>
#include <QMouseEvent>
#include <QPainter>
#include <QMessageBox>
#include <QVBoxLayout>
#include <QLabel>
#include <QPushButton>


class game_qt: public QWidget{
    Q_OBJECT
public:
    game_qt(int level);
    void paintEvent(QPaintEvent *) override;
    void keyPressEvent(QKeyEvent *event) override;
    void drawing_field(QPainter* painter);
    void set_name(std::string name);
    void resizeEvent(QResizeEvent *event) override;
private slots:
    void restartGame();
    void onTimeout();
private:
    double game_time;
    std::string player_name;
    QTimer *timer;
    QLabel* timer_label;
    int cur_level;
    game socoban_game;
    QColor field_color = QColor("#e9c46a");
    QPushButton* restart_button;
};

#endif // GAME_QT_H
