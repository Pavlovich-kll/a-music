import React, { useContext } from 'react';
import LandingWrapper, { SignUpLandingButton } from './styled';
import { AuthSchemeContext } from '../../HOC/withSchemaContext';
import { AUTH_SCHEME } from '../../HOC/withSchemaContext';

const LandingHomePage = () => {
  const { handleUpdate } = useContext(AuthSchemeContext);

  return (
    <LandingWrapper>
      <div className="container">
        <div className="block-title">
          <span className="title-main">Discover your perfect sound</span>
          <span className="description-main">Only high quality music for users</span>
          <SignUpLandingButton onClick={handleUpdate(AUTH_SCHEME)}>Get started</SignUpLandingButton>
        </div>
        <div className="arrow-7">
          <span />
          <span />
          <span />
        </div>
        <div className="block-info">
          <div className="left-block">
            <div className="play-icon">
              <svg width="80" height="45" viewBox="0 0 80 45" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="22.5" cy="22.5" r="22" stroke="#FEDA00" />
                <path
                  d="M33.377 20.885C34.3632 21.4649 34.3632 22.8911 33.377 23.4711L17.5486 32.778C16.5486 33.3659 15.2883 32.6449 15.2883 31.4849V12.8711C15.2883 11.7111 16.5487 10.9901 17.5486 11.5781L33.377 20.885Z"
                  stroke="#FEDA00"
                />
              </svg>
            </div>
            <span className="description-left-title">Listen to music you love</span>
            <span className="description-left-span">
              Enjoy audio content where and when you want it. Wherever you are, A-music is here to bring you closer to
              artists you listen to.
            </span>
          </div>
          <div className="right-block">
            <div className="block-right-text">
              <span className="title-right-span">Your favorite songs in one place</span>
              <span className="text-right-span">
                Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical
                Latin literature from 45 BC, making it over 2000 years old.
              </span>
            </div>
            <div className="block-right-text">
              <span className="title-right-span">Explore</span>
              <span className="text-right-span">
                Explore your favorite albums, tracks, playlists and artists and enter into the music world.
              </span>
            </div>
            <div className="block-right-text">
              <span className="title-right-span">Recommendation service</span>
              <span className="text-right-span">
                It is a long established fact that a reader will be distracted by the readable content of a page when
                looking at its layout, contrary to popular belief.
              </span>
            </div>
          </div>
        </div>
      </div>
      <div className="landing-footer">
        <div className="container-footer">
          <span className="landing-footer-span">© Copyright 2020. All Rights Reserved.</span>
          <span className="landing-footer-span">Privacy Policy</span>
          <span className="landing-footer-span">Terms and conditions</span>
          <span className="landing-footer-span">Support</span>
          <span className="landing-footer-span">Contact</span>
        </div>
      </div>
    </LandingWrapper>
  );
};

export default LandingHomePage;
