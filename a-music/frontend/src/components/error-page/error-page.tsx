import React from 'react';
import { useLocation } from 'react-router-dom';
import ErrorPageView, { LinkStyled } from './styled';

import { NOT_FOUND, NOT_FOUND_DESC, SERVER_ERROR_DESC } from './constants';

const ErrorPage = () => {
  const location = useLocation();
  const [errorCode] = location.search.match(/\d\d\d/);
  const errorDescription = errorCode === NOT_FOUND ? NOT_FOUND_DESC : SERVER_ERROR_DESC;

  return (
    <ErrorPageView>
      <div className="error-container">
        <div className="error-header">
          <h1>{errorCode} Error</h1>
        </div>
        <div className="error-description">
          <p>{errorDescription}</p>
        </div>
        <LinkStyled to="/">Visit Homepage</LinkStyled>
      </div>
    </ErrorPageView>
  );
};

export default ErrorPage;
