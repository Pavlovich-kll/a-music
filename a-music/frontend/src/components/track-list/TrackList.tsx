import React from 'react';
import TrackListView from './styles';
import List from '@material-ui/core/List';
import { pathOr } from 'ramda';
import { IDType, ITrack } from '../../reducers/reducers';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Avatar from '@material-ui/core/Avatar';
import ListItemText from '@material-ui/core/ListItemText';
import { IconButton } from '@material-ui/core';
import FavoriteBorderIcon from '@material-ui/icons/FavoriteBorder';
import CloseIcon from '@material-ui/icons/Close';
import { removeFromPlaylist } from '../../actions/content';
import { useDispatch, useSelector } from 'react-redux';
import { setLikeToTrack } from '../../actions/content-async';

export type ITrackListItemProps = {
  currentTrack: ITrack;
  currentPlayList: ITrack[];
  setIsPlay(a: boolean): void;
  setToPlay(id: string | number): void;
};

export const TrackList = ({ currentPlayList, setIsPlay, setToPlay, currentTrack }: ITrackListItemProps) => {
  const dispatch = useDispatch();
  const currentTrackId = pathOr(null, ['track_id'], currentTrack);
  const showDeleteBtn = (track_id: string) => track_id !== currentTrackId || currentPlayList.length === 1;

  const handleClickRemove = (id: string | number) => (e: React.MouseEvent) => {
    e.stopPropagation();
    dispatch(removeFromPlaylist(id));
  };

  const handleClickPlay = (id: IDType) => () => {
    setIsPlay(true);
    setToPlay(id);
  };
  const handleClickLike = (trackId: IDType) => (e: React.MouseEvent) => {
    e.stopPropagation();
    dispatch(setLikeToTrack(trackId || currentTrackId));
  };

  return (
    <>
      <TrackListView>
        <List>
          {currentPlayList.map(({ track_id, title, author, cover_id }: ITrack) => (
            <ListItem button key={track_id} onClick={handleClickPlay(track_id)} selected={currentTrackId === track_id}>
              <ListItemAvatar>
                <Avatar
                  variant="rounded"
                  src={`${process.env.API_URL}music-service/content/file/${cover_id}` || '/static/images/avatar/1.jpg'}
                />
              </ListItemAvatar>
              <ListItemText primary={author} secondary={title} />
              <IconButton
                className={showDeleteBtn(track_id) ? 'like-button-queue' : 'like-button-queue disappear'}
                onClick={handleClickLike(track_id)}
              >
                <FavoriteBorderIcon />
              </IconButton>
              {showDeleteBtn(track_id) && (
                <IconButton className="delete-button-queue" onClick={handleClickRemove(track_id)}>
                  <CloseIcon />
                </IconButton>
              )}
            </ListItem>
          ))}
        </List>
      </TrackListView>
    </>
  );
};

export default TrackList;
