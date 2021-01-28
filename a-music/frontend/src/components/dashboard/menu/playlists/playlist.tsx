import React, { Dispatch, useCallback } from 'react';
import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { addToPlaylist, clearPlaylist, setIsPlay } from '../../../../actions/content';
import { PlaylistButton } from '../../styled';
import Play from '../../assets/Play.png';
import Delete from '../../assets/Delete.png';
import Stop from '../../assets/Stop.png';

import { ITrack } from '../../../../reducers/reducers';
import { removePlayList } from '../../../../actions/content-async';

interface IPlayListComponent {
  pic: string;
  title: string;
  track_count: number;
  _id: number | string;
  tracks: ITrack[];
  isPlaying: boolean;
  setIsPlaying: Dispatch<string>;
}

const Playlist = ({ _id, pic, title, track_count, tracks, isPlaying, setIsPlaying }: IPlayListComponent) => {
  const dispatch = useDispatch();

  const handleTogglePlay = () => {
    if (isPlaying) {
      setIsPlaying('');
      dispatch(setIsPlay(false));
      return;
    }

    dispatch(clearPlaylist());
    dispatch(addToPlaylist(tracks, true));
    dispatch(setIsPlay(true));
    setIsPlaying(String(_id));
  };

  const handleRemovePlaylist = useCallback(() => dispatch(removePlayList(_id)), [_id, dispatch]);

  return (
    <>
      <div className={'playlist-body'}>
        <Link to={`/collection/${_id}`}>
          <div className={'playlist-img'}>
            <img src={`${process.env.API_URL}music-service/content/file/${pic}`} alt=""></img>
          </div>
          <div className={'playlist-title'}>{title}</div>
          <div className={'playlist-amount'}>{track_count} tracks</div>
        </Link>
        <div className={'playlist-body-hover'}>
          <PlaylistButton onClick={handleRemovePlaylist}>
            <img src={Delete} alt=""></img>
          </PlaylistButton>
          <PlaylistButton onClick={handleTogglePlay}>
            <img src={isPlaying ? Stop : Play} alt=""></img>
          </PlaylistButton>
        </div>
      </div>
    </>
  );
};

export default Playlist;
