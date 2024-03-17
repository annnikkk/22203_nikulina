#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include "game_qt.h"
#include <QApplication>
#include <QWidget>
#include <QPushButton>
#include <QVBoxLayout>
#include <QFont>
#include <QPalette>
#include <QTableWidget>
#include <QStringList>
#include <QTableWidgetItem>
#include <QTimer>
#include <QInputDialog>
#include <QHeaderView>


class levelSelectionWindow : public QWidget {
public:
    levelSelectionWindow(QWidget *parent = nullptr);
private:
    QPushButton *level1Button;
    QPushButton *level2Button;
    QPushButton *level3Button;
    QPushButton *level4Button;
    QPushButton *level5Button;
    QVBoxLayout *layout;

private slots:
    void onLevel1ButtonClicked();
    void onLevel2ButtonClicked();
    void onLevel3ButtonClicked();
    void onLevel4ButtonClicked();
    void onLevel5ButtonClicked();
};



class ScoreTableWindow : public QWidget {
public:
    ScoreTableWindow(const std::string& path);
private:
    QTableWidget* tableWidget;
    QVBoxLayout *layout;
};



class leaderboardWindow : public QWidget {
public:
    leaderboardWindow(QWidget *parent = nullptr);

private slots:
    void onLevel_1ButtonClicked();
    void onLevel_2ButtonClicked();
    void onLevel_3ButtonClicked();
    void onLevel_4ButtonClicked();
    void onLevel_5ButtonClicked();
};



class mainWindow : public QWidget {
public:
    mainWindow(QWidget *parent = nullptr);

private:
    QPushButton *playButton;
    QPushButton *leaderboardButton;

private slots:
    void onleaderboardButtonClicked(int level);
    void onPlayButtonClicked();
};

#endif
