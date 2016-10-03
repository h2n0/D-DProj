const {app, BrowserWindow} = require('electron');

// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let win

function createWindow () {
  win = new BrowserWindow({width: 400, height: 600, resizable: false});

  win.loadURL(`file://${__dirname}/index.html`);
  win.setMenu(null);
  //console.log("Hello world!");
  // Emitted when the window is closed.
  win.on('closed', () => {
    win = null;
  });
}

app.on('ready', createWindow);
app.on('ready-to-show', () => {
  app.show();
});

// Quit when all windows are closed.
app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('activate', () => {
  if (win === null) {
    createWindow();
  }
});
