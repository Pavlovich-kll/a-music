import React, { Suspense } from 'react';
import { useSelector } from 'react-redux';
import WrapperView, { SuspenseComponent } from './styles';
import { Spin } from 'antd';
import Header from '../header';
import Aside from '../aside';
import Player from '../player';
import { IStore } from '../../store';

interface IWrapperProps {
  children: React.ReactNode;
}

const Fallback = () => (
  <SuspenseComponent>
    <Spin tip="Loading..." size="large" />
  </SuspenseComponent>
);

const Wrapper = ({ children }: IWrapperProps) => {
  const userCurrent = useSelector(({ user }: IStore) => user.current);
  const contentCurrent = useSelector(({ content }: IStore) => content.current);
  const contentCurrentPlayList: boolean = useSelector(({ content }: IStore) => !!content.currentPlayList.length);
  const showPlayer = !!(contentCurrent || contentCurrentPlayList) && !!userCurrent;

  return (
    <WrapperView style={userCurrent ? null : { gridTemplateRows: '92px 1fr 64px' }}>
      <Header />
      <main className={userCurrent ? '' : 'landing-main'}>
        {!!userCurrent && <Aside />}
        <div className="content" id="content-id">
          <Suspense fallback={<Fallback />}>{children}</Suspense>
        </div>
      </main>
      {showPlayer && <Player />}
    </WrapperView>
  );
};

export default Wrapper;
