import React from 'react';
import { AdditionalControlView } from './styles';
import { LikeButton } from './player-like';
import PlayerVolume from './player-volume';
import { RepeatTrackSVG, ShuffleSVG } from './svg/player-additional-controls-panel-svg';
import { IconButton } from '@material-ui/core';

interface IState {
  loop?: boolean;
  isRandom?: boolean;
  volume: number;
  likes: number;
}

interface IAdditionalControlsPanelProps {
  state: IState;
  isRandom: boolean;
  loop: boolean;
  onChangeVolume(value: number): void;
  onChangeState(property: string): () => void;
}

const AdditionalControlsPanel = ({
  state,
  onChangeState,
  onChangeVolume,
  isRandom,
  loop,
}: IAdditionalControlsPanelProps) => {
  const { volume, likes } = state;

  return (
    <AdditionalControlView data-testid="additional-controls" loop={loop} isRandom={isRandom}>
      <IconButton
        color="inherit"
        onClick={onChangeState('loop')}
        data-testid="loop"
        className={loop ? 'icon-button active' : 'icon-button'}
      >
        <RepeatTrackSVG />
      </IconButton>
      <IconButton
        color="inherit"
        onClick={onChangeState('random')}
        className={isRandom ? 'icon-button active' : 'icon-button'}
      >
        <ShuffleSVG />
      </IconButton>
      <PlayerVolume volume={volume} onChange={onChangeVolume} />
      <LikeButton trackId={null} likes={likes} isRenderCounter={true} />
    </AdditionalControlView>
  );
};

export default AdditionalControlsPanel;
