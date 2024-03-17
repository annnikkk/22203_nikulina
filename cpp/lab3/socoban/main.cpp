#include "mainwindow.h"

int main(int argc, char *argv[]) {
    QApplication a(argc, argv);

    mainWindow MainWindow;
    MainWindow.show();

    return a.exec();
}
