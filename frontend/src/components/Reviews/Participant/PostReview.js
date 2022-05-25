import { Dialog } from "@mui/material";

export default function PostReview({ open, handleClose }) {
  return (
    <div>
      <Dialog open={open} onClose={handleClose}></Dialog>
    </div>
  );
}
