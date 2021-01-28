import React, { useEffect } from 'react';
import { Icon } from 'antd';
import HeaderView from './styles';
import { Link } from 'react-router-dom';
import SearchPanel from '../search-panel/index';
import Button from '@material-ui/core/Button';
import Avatar from '@material-ui/core/Avatar';
import { useDispatch } from 'react-redux';
import { getNotifications } from '../../actions/user';
import { getBodyNotifications } from '../../actions/user';
import { getAllPlaylists } from '../../actions/content-async';

type IHeaderViewProps = {
  current: any;
  getCities(): void;
  getCountries(): void;
  setToPlay(): void;
  signOut(): void;
};

export const MainHeader = ({ current, getCities, getCountries, setToPlay, signOut }: IHeaderViewProps) => {
  const dispatch = useDispatch();

  useEffect(() => {
    async function executeFunction() {
      await getCities();
      await getCountries();
      dispatch(getNotifications(current.id));
      dispatch(getBodyNotifications());
      dispatch(getAllPlaylists());
    }
    executeFunction();
  }, []);

  return (
    <HeaderView>
      <Link to="/" className="logo">
        <div className="logo-img"></div>
      </Link>
      <SearchPanel setToPlay={setToPlay} addTrackInPlayList={null} setNullSearch={null} />
      <div className="auth-control">
        <Link to="/personal-area" className="user-area">
          <Button>
            <Avatar src="/static/images/avatar/1.jpg" />
            <span className="username">{current.username}</span>
          </Button>
        </Link>
        <Link to="/send-music" className="link">
          Add track
        </Link>
        <Link to="/" onClick={signOut} className="logout-link-elem">
          <Icon type="logout" className="logout-link" />
        </Link>
      </div>
    </HeaderView>
  );
};
