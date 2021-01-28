import React, { createContext, useMemo, useState } from 'react';
import { Provider } from 'react-redux';
import { ThemeProvider } from 'styled-components';
import { Normalize } from 'styled-normalize';
import { BrowserRouter } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Theme } from './common/styles/theme';
import { ThemeDark } from './common/styles/themeDark';
import GlobalStyles from './common/styles/global';
import Wrapper from './components/wrapper';
import ApplicationStore from './store';
import RouterHandler from './components/application-router';
import LandingWrapper from './components/LandingWrapper';
import withSchemaContext from './HOC/withSchemaContext';
import { withAuthContext } from './HOC/withAuthContext';

const INITIAL_VALUES = localStorage.getItem('theme');
const isToken = localStorage.getItem('JWT_TOKEN_KEY');

export const ThemeContext = createContext<any>(INITIAL_VALUES);
ThemeContext.displayName = 'ThemeContext';

const App = () => {
  const [theme, setTheme] = useState<string>(INITIAL_VALUES);
  const [isCurrent, setCurrent] = useState<string>(isToken);
  const handleUpdateTheme = (theme: string) => {
    setTheme(theme);
  };
  const handleChangeWrapper = (isCurr: string) => {
    setCurrent(isCurr);
  };
  const actualTheme = useMemo(() => (theme === 'light' ? Theme : ThemeDark), [theme]);
  const Component = useMemo(() => (isCurrent ? Wrapper : LandingWrapper), [isCurrent]);

  return (
    <React.Fragment>
      <Normalize />
      <GlobalStyles />
      <ToastContainer />
      <BrowserRouter>
        <Provider store={ApplicationStore}>
          <ThemeContext.Provider value={{ theme, setTheme, handleUpdateTheme, handleChangeWrapper }}>
            <ThemeProvider theme={actualTheme}>
              <Component>
                <RouterHandler />
              </Component>
            </ThemeProvider>
          </ThemeContext.Provider>
        </Provider>
      </BrowserRouter>
    </React.Fragment>
  );
};

export default withSchemaContext(withAuthContext(App));
