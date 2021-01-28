import { createSelector } from 'reselect';
import { pathOr, findIndex } from 'ramda';
import { ITrack } from '../../reducers/reducers.d';
import { IStore } from '../../store';
import { convertArrayToList } from '../../helpers/arrayToList';

const audioSelector = (state: IStore): ITrack[] => pathOr([], ['content', 'audio'], state);
const currentSelector = (state: IStore): ITrack[] => pathOr([], ['content', 'current'], state);
const playListSelector = (state: IStore): string[] | number[] => pathOr([], ['content', 'playList'], state);

export const currentPlayListSelector = createSelector(
  [playListSelector, currentSelector],
  (audioCollection: any, { track_id }: any) => {
    const audioCollectionChunk: ITrack[] = audioCollection;
    return convertArrayToList(audioCollectionChunk);
  }
);

export const currentPlaySelector = createSelector(
  [playListSelector, currentSelector],
  (audioCollection: any, { track_id }: any) => {
    const currentTrackIndex: number = findIndex((audio: ITrack) => audio.track_id === track_id)(audioCollection);
    const audioCollectionChunk: ITrack[] = audioCollection.slice(currentTrackIndex);
    return convertArrayToList(audioCollectionChunk);
  }
);

export const currentTrackSelector = createSelector(
  [audioSelector, currentSelector],
  (audioCollection: any, { track_id }: any) => {
    const currentTrackIndex: number = findIndex((audio: ITrack) => audio.track_id === track_id)(audioCollection);
    const audioCollectionChunk: ITrack[] = audioCollection.slice(currentTrackIndex);
    return convertArrayToList(audioCollectionChunk);
  }
);
