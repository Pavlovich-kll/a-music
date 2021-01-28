import styled from 'styled-components';

export default styled.div`
  padding: 3rem 0;
  width: 100%;
  display: grid;
  grid-template-rows: 1fr;
  justify-items: stretch;
  font-family: Open Sans, Arial, sans-serif;
  color: ${({ theme: { colors } }) => colors.secondary.textUnvisible};

  .collection__header {
    margin-bottom: 1rem;
    display: grid;
    grid-template-columns: 1fr 5fr;

    &-image {
      img {
        max-width: 300px;
        max-height: 300px;
        border-radius: 0.5rem;
      }
    }

    &-description {
      margin-left: 2rem;
      display: grid;

      &-title {
        h1,
        h2 {
          margin-bottom: 0.75rem;
          text-align: justify;
        }

        &-playlist-name {
          color: ${({ theme: { colors } }) => colors.primary.baseText};
        }

        &-top-artist {
          color: ${({ theme: { colors } }) => colors.primary.baseText};
          font-weight: 400;
          letter-spacing: 0.25px;
        }

        &-write-up {
          color: ${({ theme: { colors } }) => colors.secondary.grayText};
          font-weight: 400;
        }
      }

      &-summary {
        align-self: end;

        p {
          margin-bottom: 0;
          font-size: 0.75rem;
          color: ${({ theme: { colors } }) => colors.secondary.grayText};
        }
      }
    }
  }

  .collection__body {
    &-toolbar {
      margin-bottom: 1rem;
      padding: 0.5rem 0;
      display: grid;
      grid-template-columns: 1fr 1fr;
      justify-content: space-between;

      &-left {
        p {
          margin-bottom: 0;
        }
      }

      &-right {
        justify-self: right;

        p {
          margin-bottom: 0;
        }
      }
    }

    &-table {
      width: 100%;
      display: table;
      font-size: 0.875rem;
      text-align: start;

      th,
      td {
        padding: 0.5rem 0 0.5rem 1rem;
        border-bottom: 1px solid #32323d;
      }

      th {
        font-weight: 300;
        color: ${({ theme: { colors } }) => colors.primary.baseText};
        border-top: 1px solid #32323d;
      }

      .play-icon {
        color: ${({ theme: { colors } }) => colors.primary.brandColor};
      }

      .like-button {
        color: ${({ theme: { colors } }) => colors.primary.brandColor};
      }

      th:nth-of-type(1),
      th:nth-of-type(2),
      td:nth-of-type(1),
      td:nth-of-type(2) {
        width: 2%;
      }

      th:nth-of-type(3),
      td:nth-of-type(3) {
        width: 30%;
      }

      th:nth-of-type(4),
      th:nth-of-type(5),
      td:nth-of-type(4),
      td:nth-of-type(5) {
        width: 20%;
      }

      th:nth-of-type(7),
      td:nth-of-type(7) {
        width: 5%;
      }

      th:first-of-type,
      td:first-of-type {
        padding-left: 1rem;
        color: ${({ theme: { colors } }) => colors.primary.baseText};
      }

      th:last-of-type,
      td:last-of-type {
        width: 2%;
        padding-right: 1rem;
      }
    }
  }

  @media (max-width: 1024px) {
    .collection__body-table {
      thead {
        display: none;
        position: absolute;
      }

      tr {
        margin-bottom: 2rem;
        display: block;
      }

      td {
        display: block;
        text-align: right;

        &:nth-of-type(1),
        &:nth-of-type(3),
        &:nth-of-type(4),
        &:nth-of-type(5),
        &:nth-of-type(6),
        &:nth-of-type(7),
        &:nth-of-type(8) {
          width: 100%;
        }

        &:nth-of-type(2) {
          display: none;
        }

        &:nth-of-type(8) {
          padding-right: 0;
        }
      }

      td::before {
        content: attr(data-label);
        float: left;
        font-weight: bold;
        text-transform: uppercase;
      }
    }
  }

  @media (max-width: 768px) {
    padding: 1rem 0;

    .collection__header {
      grid-template-columns: none;
      justify-items: center;

      &-image {
        margin-bottom: 0.5rem;
      }

      &-description {
        margin: 0 0.5rem;

        h1 {
          text-align: center;
        }
      }
    }

    .collection__body-toolbar {
      padding: 0.5rem;
    }

    .collection__body-table {
      td {
        padding: 0.25rem 0.75rem;

        &:nth-of-type(8) {
          padding-right: 0.75rem;
        }
      }
    }
  }
  .collection__body-searchpanel {
    position: absolute;
    right: 3%;
    width: 50%;
    box-shadow: 0 10px 10px rgba(0, 0, 0, 0.2), 5px 10px 5px rgba(0, 0, 0, 0.23);
  }
`;
