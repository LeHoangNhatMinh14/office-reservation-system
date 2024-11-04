import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App'; // Import your main App component
import './index.css'; // Import any global CSS styles


const root = createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
