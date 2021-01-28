import React from 'react';
import { MainHeader } from './MainHeader';
import { LandingHeader } from './LandingHeader';

export default class Header extends React.Component<any, any> {
  constructor(props: any) {
    super(props);
    this.state = {
      isScrolled: false,
    };
  }

  listenScrollEvent = (e: any) => {
    const el = document.getElementById('content-id');
    //@ts-ignore
    const offSet = el.pageYOffset || el.scrollTop;
    if (offSet > 92) {
      this.setState({ isScrolled: true });
    } else {
      this.setState({ isScrolled: false });
    }
  };

  componentDidMount(): void {
    document.getElementById('content-id').addEventListener('scroll', this.listenScrollEvent);
    this.props.getCurrentUser();
  }

  componentWillUnmount(): void {
    document.getElementById('content-id').removeEventListener('scroll', this.listenScrollEvent);
  }

  render() {
    const { current, signOut, setToPlay, getCities, getCountries } = this.props;
    const { isScrolled } = this.state;

    return (
      <>
        {current ? (
          <MainHeader
            current={current}
            getCities={getCities}
            getCountries={getCountries}
            signOut={signOut}
            setToPlay={setToPlay}
          />
        ) : (
          <LandingHeader isScrolled={isScrolled} />
        )}
      </>
    );
  }
}
