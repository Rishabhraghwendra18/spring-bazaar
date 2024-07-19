import { NextResponse } from "next/server";

export function middleware(request) {
  const isAuthenticated = request.cookies.get("Authorization");

  if (!isAuthenticated) {
    return NextResponse.redirect(new URL("/", request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/dashboard", "/verify"],
};
