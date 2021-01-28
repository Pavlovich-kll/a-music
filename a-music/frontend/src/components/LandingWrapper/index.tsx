import React from 'react';
import WrapperView from './styles';
import LandingHomePage from '../landing-home-page';
import Header from '../header';

class LandingWrapper extends React.Component<any, any> {
  render() {
    return (
      <WrapperView>
        <Header />
        <div className={'landing-main'}>
          <div className="content" id="content-id">
            <LandingHomePage />
          </div>
        </div>
      </WrapperView>
    );
  }
}

export default LandingWrapper;
