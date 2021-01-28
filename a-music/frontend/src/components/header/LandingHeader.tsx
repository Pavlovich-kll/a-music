import React from 'react';
import LandingHeaderView from './styleLandingPage';
import { Link } from 'react-router-dom';
import AuthenticationButtons from '../authentication-header';

type ILandingHeaderView = {
  isScrolled: boolean;
};

export const LandingHeader = ({ isScrolled }: ILandingHeaderView) => {
  return (
    <LandingHeaderView isScrolled={isScrolled}>
      <div className="container-header">
        <Link to="/" className="logo-link">
          <div className="logo-img" />
          <span className="a-music-span-logo">A-music</span>
        </Link>
        <AuthenticationButtons />
      </div>
    </LandingHeaderView>
  );
};
