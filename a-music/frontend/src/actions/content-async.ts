import { CONTENT_ACTION_TYPE } from '../constants';
import HTTP from '../common/api';
import { toast } from 'react-toastify';
import { IContentState, IDType, IPlayList, ITrack } from '../reducers/reducers';
import { Dispatch } from 'react';
import { map, path, propEq, reject } from 'ramda';
import { IFormUploadMusic } from '../components/send-music/send-music';
import { IFormCreatePlaylist } from '../components/create-playlist/create-playlist';

const REMOVE_PLAYLIST: string = `${process.env.API_URL}user-service/playlist`;
const PLAYLISTS_ENDPOINT: string = '/music-service/playlist';
const PLAYLIST_NEW_ENDPOINT: string = '/music-service/playlist/new';
const FILE_ENDPOINT: string = '/music-service/file';
const TYPE_MUSIC_ENDPOINT: string = '/music-service/content/types';
const DUPLICATE_ERROR_CODE: number = 11000;
const CITIES_ENDPOINT: string = '/user-service/cities';
const COUNTRIES_ENDPOINT: string = '/user-service/countries';

interface IUploadMusic extends IFormUploadMusic {
  [key: string]: any;

  genres: string[];
  cover: File;
  track: File;
}

interface IResponseUploadMusic extends ITrack {
  response: any;
}

interface IUploadPlayList extends IFormCreatePlaylist {
  [key: string]: any;

  pic: File;
  tracks: string[];
}

export const removePlayList = (id: IDType) => async (dispatch: Dispatch<object>) => {
  try {
    await HTTP.delete(`${REMOVE_PLAYLIST}/${id}`);

    dispatch({
      type: CONTENT_ACTION_TYPE,
      payload: (state: IContentState): IContentState => ({
        ...state,
        playLists: state.playLists.filter(({ _id }: IPlayList) => _id !== id),
      }),
    });
  } catch (e) {
    toast.error(`Playlist hasn't been deleted`);
  }
};

const _findAndReplaceTrack = (updatedTrack: ITrack, currentTrack: ITrack) => {
  if (updatedTrack.track_id === currentTrack.track_id) return updatedTrack;
  return currentTrack;
};

export const setLikeToTrack = (trackId: string) => async (dispatch: Dispatch<object>) => {
  try {
    const updatedTrack: any = await HTTP.put(FILE_ENDPOINT, {
      track_id: trackId,
    });

    dispatch({
      type: CONTENT_ACTION_TYPE,
      payload: (state: IContentState): IContentState => ({
        ...state,
        audio: map(_findAndReplaceTrack.bind(null, updatedTrack))(state.audio),
        current: state.current.track_id === updatedTrack.track_id ? updatedTrack : state.current,
        currentPlayList: map(_findAndReplaceTrack.bind(null, updatedTrack))(state.currentPlayList),
      }),
    });
  } catch (error) {
    toast(error.message);
  }
};

export const getAllMusic = () => async (dispatch: Dispatch<object>) => {
  const response: [ITrack] = await HTTP.get(FILE_ENDPOINT);

  dispatch({
    type: CONTENT_ACTION_TYPE,
    payload: (state: IContentState): IContentState => ({
      ...state,
      audio: response,
    }),
  });
};

export const uploadMusic = ({ genres = [], ...track }: IUploadMusic) => async (dispatch: Dispatch<object>) => {
  const formData = new FormData();
  genres.forEach((data: string) => formData.append('genres', data));
  Object.keys(track).forEach((key: string) => {
    formData.append(key, track[key]);
  });

  try {
    const { response, ...uploadedTrack }: IResponseUploadMusic = await HTTP.post(FILE_ENDPOINT, formData);
    const isDuplicate: boolean = path(['data', 'error', 'code'], response) === DUPLICATE_ERROR_CODE;

    if (isDuplicate) throw Error('Duplicate track');

    toast.success('Success');

    dispatch({
      type: CONTENT_ACTION_TYPE,
      payload: (state: IContentState): IContentState => ({
        ...state,
        audio: [...state.audio, uploadedTrack],
      }),
    });
  } catch (error) {
    toast.error(error.message);
  }
};

export const getTypes = () => HTTP.get(TYPE_MUSIC_ENDPOINT);

export const getAllPlaylists = () => async (dispatch: Dispatch<object>) => {
  const response: IPlayList[] = await HTTP.get(PLAYLISTS_ENDPOINT);
  dispatch({
    type: CONTENT_ACTION_TYPE,
    payload: (state: IContentState): IContentState => ({
      ...state,
      playLists: response,
    }),
  });
};

export const getPLayList = async (id: string) => {
  const response: IPlayList = await HTTP.get(`${PLAYLISTS_ENDPOINT}/${id}`);
  return response;
};

export const uploadPlayList = ({ tracks, ...playlistPayload }: IUploadPlayList) => async (
  dispatch: Dispatch<object>
) => {
  const formData = new FormData();
  tracks.forEach((item: string) => formData.append('tracks', item));
  Object.keys(playlistPayload).forEach((key: string) => {
    formData.append(key, playlistPayload[key]);
  });
  try {
    const response: IPlayList = await HTTP.post(PLAYLISTS_ENDPOINT, formData);

    toast.success('Success');

    dispatch({
      type: CONTENT_ACTION_TYPE,
      payload: (state: IContentState): IContentState => ({
        ...state,
        playLists: [...state.playLists, response],
      }),
    });
  } catch (error) {
    toast.error(error.message);
  }
};

export const updateTrackInPlayList = (playlist: IPlayList, tracks: ITrack[]) => async (dispatch: Dispatch<object>) => {
  try {
    const response: IPlayList = await HTTP.put(PLAYLIST_NEW_ENDPOINT, {
      id: playlist._id,
      tracks: tracks.map((item: ITrack) => item._id),
    });

    dispatch({
      type: CONTENT_ACTION_TYPE,
      payload: (state: IContentState): IContentState => ({
        ...state,
        playLists: [...state.playLists.filter((item: IPlayList) => item._id !== playlist._id), response],
      }),
    });
  } catch (error) {
    toast(error.message);
  }
};

export const getCountries = () => async (dispatch: Dispatch<object>) => {
  const countries: [] = await HTTP.get(COUNTRIES_ENDPOINT);
  dispatch({
    type: CONTENT_ACTION_TYPE,
    payload: (state: IContentState): IContentState => ({
      ...state,
      countries: countries,
    }),
  });
};

export const getCities = () => async (dispatch: Dispatch<object>) => {
  const cities: [] = await HTTP.get(CITIES_ENDPOINT);
  dispatch({
    type: CONTENT_ACTION_TYPE,
    payload: (state: IContentState): IContentState => ({
      ...state,
      cities: cities,
    }),
  });
};
