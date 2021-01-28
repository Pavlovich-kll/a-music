import React from 'react'
import PlayerControl from '../player-control'
import {fireEvent, render} from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect';


describe('Player Control', () => {
  const props = {
    onTogglePlay: jest.fn(),
    onClickNext: jest.fn(),
    onClickPrevious: jest.fn(),
    isPlaying: jest.fn()
  }

  it('should render all components', () => {
    const {getByTestId} = render(<PlayerControl {...props} />);
    expect(getByTestId('prev-button')).toBeInTheDocument();
    expect(getByTestId('next-button')).toBeInTheDocument();
    expect(getByTestId('play-pause')).toBeInTheDocument();
  });

  it('function onTogglePlay should be called', () => {
    const {getByTestId} = render(<PlayerControl {...props} />);
    fireEvent.click(getByTestId('play-pause'));
    expect(props.onTogglePlay).toBeCalled();
  });

  it('function onClickNext should be called', () => {
    const {getByTestId} = render(<PlayerControl {...props} />);
    fireEvent.click(getByTestId('next-button'));
    expect(props.onClickNext).toBeCalled();
  });

  it('function onClickPrevious should be called', () => {
    const {getByTestId} = render(<PlayerControl {...props} />);
    fireEvent.click(getByTestId('prev-button'));
    expect(props.onClickPrevious).toBeCalled();
  });

  it('should not render `previous` button components', () => {
    const {queryByTestId} = render(<PlayerControl
      onTogglePlay={jest.fn()}
      onClickNext={jest.fn()}
      isPlaying = {jest.fn()}
    />);
    expect(queryByTestId('prev-button')).not.toBeInTheDocument();
  });
});
