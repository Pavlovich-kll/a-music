import { find, forEach, propEq, reject } from 'ramda';
import { CONTENT_ACTION_TYPE } from '../constants';
import { ITrack, IContentState, IDType } from '../reducers/reducers.d';

export const setIsPlay = (isPlay: boolean) => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState => ({
    ...state,
    isPlay: isPlay,
  }),
});

export const setIsRandom = (isRandom: boolean) => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState => ({
    ...state,
    isRandom: isRandom,
    isLoop: isRandom ? false : state.isLoop,
  }),
});

export const setIsLoop = (isLoop: boolean) => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState => ({
    ...state,
    isRandom: isLoop ? false : state.isRandom,
    isLoop: isLoop,
  }),
});

export const addTrackInPlayList = (target: ITrack) => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState => ({
    ...state,
    createPlayListTracks: [...state.createPlayListTracks, target],
  }),
});

export const clearTracksInPlayList = () => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState => ({
    ...state,
    createPlayListTracks: [],
  }),
});

export const deleteTrackInPlayList = (id: IDType) => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState => ({
    ...state,
    createPlayListTracks: reject(propEq('track_id', id))(state.createPlayListTracks),
  }),
});

export const deleteTrackFromPlaylist = (data: any) => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState => ({
    ...state,
    playLists: data,
  }),
});

const _filterPlayList = ({ currentPlayList }: IContentState, tracks: ITrack[] = []) => {
  const result: ITrack[] = [];

  const iterateNewTracks = (newTrack: ITrack) => {
    let isUnique = true;

    const compareID = (oldTrack: ITrack) => {
      if (propEq('_id', newTrack._id)(oldTrack)) isUnique = false;
    };

    forEach(compareID)(currentPlayList);
    if (!isUnique) return;
    result.push(newTrack);
  };

  forEach(iterateNewTracks)(tracks);
  return [...currentPlayList, ...result];
};

export const addToPlaylist = (tracks: ITrack[], changeCurrent: boolean = false) => {
  return {
    type: CONTENT_ACTION_TYPE,
    payload: (state: IContentState): IContentState => {
      const newState = {
        ...state,
        currentPlayList: _filterPlayList(state, tracks),
        isPlay: state.currentPlayList.length ? state.isPlay : false,
      };
      if ((changeCurrent && !!newState.currentPlayList.length) || !newState.current)
        newState.current = newState.currentPlayList[0];
      return newState;
    },
  };
};

export const setToPlay = (id: IDType) => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState =>
    ({
      ...state,
      current: find(propEq('track_id', id))(state.currentPlayList),
    } as IContentState),
});

export const removeFromPlaylist = (id: IDType) => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState => ({
    ...state,
    currentPlayList: reject(propEq('track_id', id))(state.currentPlayList),
    current: state.currentPlayList.length === 1 ? null : state.current,
  }),
});

export const clearPlaylist = () => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState => ({
    ...state,
    currentPlayList: [],
  }),
});

export const updateCurrentTrack = (track: ITrack) => ({
  type: CONTENT_ACTION_TYPE,
  payload: (state: IContentState): IContentState => ({
    ...state,
    current: track,
  }),
});
