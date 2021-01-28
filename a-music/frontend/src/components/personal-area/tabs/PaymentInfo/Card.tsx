import React, { useEffect, useMemo, useState } from 'react';
import CardWrapper from './styles';
import chip from './img/chip.png';
import creditCardType from 'credit-card-type';
import cards from './constant';
import americanExpress from './img/american-express.png';
import discover from './img/discover.png';
import maestro from './img/maestro.png';
import mastercard from './img/mastercard.png';
import mir from './img/mir.png';
import visa from './img/visa.png';
import { pathOr } from 'ramda';

const CARDS_TYPE = {
  [cards.AMERICAN_EXPRESS]: americanExpress,
  [cards.VISA]: visa,
  [cards.MASTERCARD]: mastercard,
  [cards.DISCOVER]: discover,
  [cards.MIR]: mir,
  [cards.MAESTRO]: maestro,
};

type CardType = keyof typeof CARDS_TYPE;

interface IProps {
  card: any;
}

export const Card = ({ card }: IProps) => {
  const [cardType, setCardType] = useState<CardType>('');
  const CardLogo = useMemo(() => pathOr(null, [cardType], CARDS_TYPE), [cardType]);

  useEffect(() => {
    if (card) {
      setCardType(creditCardType(card.number)[0].type);
    }
  }, [card]);

  const getFormattedNumber = () => {
    if (card) {
      return card.number.replace(/\d{4}(?= \d{4})/g, '****');
    }
  };

  return (
    <CardWrapper>
      {card ? (
        <div className="cardBlock">
          <div className={`card ${cardType}`}>
            <img className="card-logo" src={CardLogo} alt="" />
            <img className="card-chip" src={chip} alt="" />
            <div className="card-number">{getFormattedNumber()}</div>
            <div className="card-expiry">
              <div className="expiry-tooltip valid">valid thru</div>
              <div className="expiry-flex">
                <div className="expiry-tooltip">month/year</div>
                <div className="expiry">{card.expiry}</div>
              </div>
            </div>
            <div className="card-name">{card.name}</div>
          </div>
        </div>
      ) : (
        <p>You did't add a card</p>
      )}
    </CardWrapper>
  );
};
