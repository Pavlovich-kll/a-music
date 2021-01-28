import React from 'react';
import DashboardView, { LinkStyled } from './styled';
import PlayLists from './menu/playlists/playlists';
import Add from './assets/Add.png';
import { IPlayList } from '../../reducers/reducers';
import { useDispatch, useSelector } from 'react-redux';
import { IStore } from '../../store';
import { CREATE_PLAYLIST_PATH } from '../../router';

const Dashboard = () => {
  const contentPlayLists: IPlayList[] = useSelector(({ content }: IStore) => content.playLists);

  return (
    <DashboardView>
      <div className={'playlist-container'}>
        <h2>PLAYLISTS</h2>
        <div className={'playlist-line'} />
        <div className={'playlist-content'}>
          <div className={'playlist-create'}>
            <LinkStyled to={CREATE_PLAYLIST_PATH} className="link">
              <img src={Add} alt=""></img>
              Create New PlayList
            </LinkStyled>
          </div>
          <PlayLists playLists={contentPlayLists} />
        </div>
      </div>
    </DashboardView>
  );
};
export default Dashboard;
