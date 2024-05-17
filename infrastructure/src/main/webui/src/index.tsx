import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { store } from './app/store';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Provider } from 'react-redux';
import 'bootstrap/dist/css/bootstrap.min.css';
import { SSEProvider } from 'react-hooks-sse';
import { v4 as uuidv4 } from 'uuid';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
const requestedBy = uuidv4();

root.render(
  <React.StrictMode>
    <SSEProvider endpoint={`/api/oneToOne/matchmaking/lifecycle/${requestedBy}/stream`}>
      <Provider store={store}>
        <App requestedBy={requestedBy} />
      </Provider>
    </SSEProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
