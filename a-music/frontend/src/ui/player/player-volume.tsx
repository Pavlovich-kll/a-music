import React, { useEffect, useMemo, useState } from 'react';
import { withStyles, createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import { Slider } from '@material-ui/core';
import IconButton from '@material-ui/core/IconButton';
import Tooltip from '@material-ui/core/Tooltip';
import { VolumeOnSVG, VolumeOffSVG } from './svg/player-additional-controls-panel-svg';

interface IPlayerVolume {
  volume: number;
  onChange(value: any): void;
}

const MySlider = withStyles({
  root: {
    color: '#E0E0E0',
    height: 8,
  },
  thumb: {
    height: 11,
    width: 11,
    backgroundColor: '#FEDA00',
    marginTop: -8,
    marginLeft: -12,
    '&:focus, &:hover, &$active': {
      boxShadow: 'inherit',
    },
  },
  active: {},
  valueLabel: {
    color: '#FEDA00',
    left: '-11px',
  },
  track: {
    color: '#FEDA00',
    height: 8,
    borderRadius: 4,
  },
  rail: {
    height: 8,
    borderRadius: 4,
  },
})(Slider);

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    tooltip: {
      backgroundColor: '#F7F7F7',
      position: 'relative',
      top: '-2px',
      left: '-9px',
      width: 43,
      height: 120,
      boxShadow: '0px 3px 20px rgba(0, 0, 0, 0.4)',
    },
  })
);

const PlayerVolume = ({ volume, onChange }: IPlayerVolume) => {
  const [sound, setSound] = useState(true);
  const [soundMemory, setSoundMemory] = useState(volume);

  const classes = useStyles();

  const VolumeIcon = useMemo(
    () => (volume > 0 ? <VolumeOnSVG data-testid="volume-up" /> : <VolumeOffSVG data-testid="volume-off" />),
    [volume]
  );

  const handleChangeVolume = (event: any, newValue: any) => {
    if (!sound) setSound(true);
    setSoundMemory(newValue);
  };

  const handleToggleSound = (): void => {
    setSound((currentSoundState) => !currentSoundState);
  };

  useEffect(() => {
    if (!sound) {
      onChange(0);
    } else {
      onChange(soundMemory);
    }
  });

  return (
    <>
      <Tooltip
        title={
          <MySlider value={volume} orientation="vertical" valueLabelDisplay="auto" onChange={handleChangeVolume} />
        }
        interactive
        classes={classes}
      >
        <IconButton onClick={handleToggleSound} color="inherit" data-testid="volume-button" className="volume-button">
          {VolumeIcon}
        </IconButton>
      </Tooltip>
    </>
  );
};

export default PlayerVolume;
