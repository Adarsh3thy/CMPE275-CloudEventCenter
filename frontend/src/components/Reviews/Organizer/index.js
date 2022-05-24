import React, { useEffect, useState } from "react";
import { Grid, Avatar, Paper, Typography, Rating } from "@mui/material";
import { getOrganizerReviews } from "../../../controllers/reviews";
import { useLocation } from "react-router-dom";

const OrganizerReviews = () => {
  const [organizerReviews, setOrganizerReviews] = useState([]);

  const imgLink =
    "https://images.pexels.com/photos/1681010/pexels-photo-1681010.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260";

  const search = useLocation().search;

  useEffect(() => {
    const organizerId = new URLSearchParams(search).get("organizerId");
    getOrganizerReviews(organizerId)
      .then((res) => setOrganizerReviews(res.data))
      .catch((err) => console.log(err));
  }, []);

  return (
    <div>
      <Grid container sx={{ mt: 3 }} alignItems="left">
        <Grid item md={12}>
          <Typography
            align="left"
            md={5}
            style={{ fontSize: 35, fontWeight: 650 }}
          >
            Organizer Reviews
          </Typography>
        </Grid>
        {organizerReviews && organizerReviews.length === 0 ? (
          <Grid container sx={{ mt: 5 }} alignItems="center">
            <Typography
              md={5}
              style={{ fontSize: 20, fontWeight: 600, marginLeft: 330 }}
            >
              No reviews posted!
            </Typography>
          </Grid>
        ) : (
          organizerReviews.map((review) => (
            <Grid container sx={{ mt: 5 }} alignItems="center">
              <Paper
                style={{
                  padding: "40px 20px",
                  marginTop: 25,
                  cursor: "pointer",
                  minWidth: "1000px",
                  marginLeft: "30px",
                }}
              >
                <Grid container wrap="nowrap" spacing={2}>
                  <Grid item>
                    <Avatar alt="Remy Sharp" src={imgLink} />
                  </Grid>
                  <Grid justifyContent="left" item xs zeroMinWidth>
                    <h4
                      style={{
                        margin: 0,
                        textAlign: "left",
                      }}
                    >
                      Anay Naik (for Google Cloud Event)
                    </h4>
                    <p>
                      <Rating value={review.rating} readOnly />
                    </p>
                    <p style={{ textAlign: "left" }}>{review.review}</p>
                    <p style={{ textAlign: "left", color: "gray" }}>
                      posted on {review.createdAt}
                    </p>
                  </Grid>
                </Grid>
              </Paper>
            </Grid>
          ))
        )}
      </Grid>
    </div>
  );
};

export default OrganizerReviews;
